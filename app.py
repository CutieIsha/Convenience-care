import os
import requests
import pandas as pd
import numpy as np
from flask import Flask, render_template, request, redirect, url_for, send_file, jsonify
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from io import BytesIO
from transformers import AutoModelForCausalLM, AutoTokenizer
import torch
import json
import joblib  # For saving/loading model

app = Flask(__name__)

# Load and prepare the data
try:
    data = pd.read_csv('disease_data.csv')  # Ensure this file is available in your directory
    description_data = pd.read_csv('description.csv')  # Ensure this file is available in your directory
    medications_data = pd.read_csv('medications.csv')  # Ensure this file is available in your directory
except Exception as e:
    print(f"Error loading data: {e}")

# Define symptoms and target variable
symptoms = list(data.columns[1:])  # All symptoms except 'Disease'
X = data[symptoms]
y = data['Disease']

# Encode the target variable
le = LabelEncoder()
y = le.fit_transform(y)

# Train the model (or load it if already trained)
model_path = 'random_forest_model.pkl'
if os.path.exists(model_path):
    model = joblib.load(model_path)
else:
    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X, y)
    joblib.dump(model, model_path)  # Save the model

# Load DialoGPT model and tokenizer for the chatbot
tokenizer = AutoTokenizer.from_pretrained("microsoft/DialoGPT-medium")
model_chatbot = AutoModelForCausalLM.from_pretrained("microsoft/DialoGPT-medium")
chat_history_ids = None  # Initialize chat history

# Define a sample list of doctors and their specializations
doctors = [
    {"name": "Dr. John Smith", "specialization": "Cardiologist"},
    {"name": "Dr. Jane Doe", "specialization": "Neurologist"},
    {"name": "Dr. Emily Johnson", "specialization": "Orthopedic Surgeon"},
]

# Create a dictionary of descriptions from the description CSV file
descriptions_dict = pd.Series(description_data.Description.values, index=description_data.Disease).to_dict()

# Create a dictionary of medications from the medications CSV file
medications_dict = pd.Series(medications_data.Medications.values, index=medications_data.Disease).to_dict()

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        selected_symptoms = request.form.getlist('symptoms')
        symptom_data = [1 if symptom in selected_symptoms else 0 for symptom in symptoms]

        # Get the prediction probabilities for all diseases
        probabilities = model.predict_proba([symptom_data])[0]
        top_diseases = sorted(zip(le.classes_, probabilities), key=lambda x: x[1], reverse=True)[:5]

        predictions = {disease: prob for disease, prob in top_diseases}
        
        top_diseases_names = [disease for disease, _ in top_diseases]
        descriptions = {disease: descriptions_dict.get(disease, "No description available.") for disease in top_diseases_names}
        medications = {disease: medications_dict.get(disease, "No medications available.") for disease in top_diseases_names}
        
        return render_template('results.html', symptoms=selected_symptoms, predictions=predictions, descriptions=descriptions, medications=medications)
    
    return render_template('index.html', symptoms=symptoms)

@app.route('/download_report')
def download_report():
    symptoms_list = request.args.get('symptoms', '').split(',')
    predictions_json = request.args.get('probabilities', '{}')
    top_diseases = request.args.get('top_diseases', '').split(',')

    try:
        # Attempt to parse the predictions JSON
        predictions_dict = json.loads(predictions_json)
        print("DEBUG: Parsed predictions_dict:", predictions_dict)  # Debugging log
    except json.JSONDecodeError:
        predictions_dict = {}
        print("Error decoding predictions JSON")

    buffer = BytesIO()
    c = canvas.Canvas(buffer, pagesize=letter)
    width, height = letter

    # Title
    c.setFont("Helvetica-Bold", 18)
    c.drawCentredString(width / 2.0, height - 72, "Health Check Report")

    # Subtitle
    c.setFont("Helvetica", 12)
    c.drawString(72, height - 120, "Patient Information")
    c.drawString(72, height - 140, f"Date: {pd.Timestamp.now().strftime('%Y-%m-%d %H:%M:%S')}")
    c.drawString(72, height - 160, f"Symptoms Reported: {', '.join(symptoms_list)}")

    # Add horizontal line
    y_position = height - 180
    c.line(72, y_position, width - 72, y_position)

    # Predicted Conditions
    y_position -= 20
    c.setFont("Helvetica-Bold", 14)
    c.drawString(72, y_position, "Predicted Conditions")

    y_position -= 20
    c.setFont("Helvetica", 12)

    # Display diseases and their probabilities
    if not top_diseases or top_diseases == ['']:
        c.drawString(72, y_position, "No predictions available.")
    else:
        for disease in top_diseases:
            prob = predictions_dict.get(disease, 0)
            print(f"DEBUG: Disease {disease}, Probability {prob}")  # Debugging log
            if prob:  # Check if there's a valid probability for the disease
                c.drawString(72, y_position, f"{disease.replace('_', ' ')}: {prob * 100:.2f}%")
                y_position -= 20  # Space between lines

    # Add space for the next section (Descriptions)
    y_position -= 10
    c.line(72, y_position, width - 72, y_position)
    y_position -= 20

    # Descriptions for the top diseases
    c.setFont("Helvetica-Bold", 14)
    c.drawString(72, y_position, "Description of Top Diseases")
    y_position -= 20

    c.setFont("Helvetica", 12)
    if not top_diseases or top_diseases == ['']:
        c.drawString(72, y_position, "No descriptions available.")
    else:
        for disease in top_diseases:
            description = descriptions_dict.get(disease, "No description available.")
            c.drawString(72, y_position, f"{disease.replace('_', ' ')}: {description}")
            y_position -= 40

    # Save and output
    c.save()
    buffer.seek(0)
    
    return send_file(buffer, as_attachment=True, download_name='Health_Check_Report.pdf', mimetype='application/pdf')

@app.route('/chatbot', methods=['POST'])
def chatbot():
    global chat_history_ids

    user_input = request.json['message']

    # Check for common symptoms in the user input
    if "fever" in user_input.lower():
        bot_response = "You mentioned having a fever. It's important to stay hydrated and rest. You may consider medications like paracetamol. If symptoms persist, please consult a doctor."
    else:
        new_user_input_ids = tokenizer.encode(user_input + tokenizer.eos_token, return_tensors='pt')

        # Append the new user input to the chat history
        bot_input_ids = torch.cat([chat_history_ids, new_user_input_ids], dim=-1) if chat_history_ids is not None else new_user_input_ids

        # Generate a response from the model
        chat_history_ids = model_chatbot.generate(bot_input_ids, max_length=1000, pad_token_id=tokenizer.eos_token_id)

        # Get the latest bot response
        bot_response = tokenizer.decode(chat_history_ids[:, bot_input_ids.shape[-1]:][0], skip_special_tokens=True)

    return jsonify({'reply': bot_response})

if __name__ == '__main__':
    app.run(debug=True)
