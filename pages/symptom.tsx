'use client'

import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"

type Message = {
  id: number
  text: string
  sender: 'user' | 'bot'
  options?: string[]
}

type Symptom = {
  name: string
  conditions: string[]
  medications: string[]
}

const symptoms: Symptom[] = [
  {
    name: "Headache",
    conditions: ["Tension headache", "Migraine"],
    medications: ["Acetaminophen", "Ibuprofen"]
  },
  {
    name: "Fever",
    conditions: ["Common cold", "Flu"],
    medications: ["Acetaminophen", "Rest and fluids"]
  },
  {
    name: "Cough",
    conditions: ["Common cold", "Bronchitis"],
    medications: ["Dextromethorphan", "Honey and lemon tea"]
  },
  {
    name: "Fatigue",
    conditions: ["Lack of sleep", "Anemia"],
    medications: ["Rest", "Iron supplements (if anemic)"]
  }
]

export default function SymptomChecker() {
  const [messages, setMessages] = useState<Message[]>([
    { id: 1, text: "Hello! I'm here to help you check your symptoms. What symptom are you experiencing?", sender: 'bot', options: symptoms.map(s => s.name) }
  ])
  const [currentSymptom, setCurrentSymptom] = useState<Symptom | null>(null)

  const addMessage = (text: string, sender: 'user' | 'bot', options?: string[]) => {
    setMessages(prevMessages => [...prevMessages, { id: prevMessages.length + 1, text, sender, options }])
  }

  const handleSymptomSelection = (symptom: string) => {
    addMessage(symptom, 'user')
    const selectedSymptom = symptoms.find(s => s.name === symptom)
    if (selectedSymptom) {
      setCurrentSymptom(selectedSymptom)
      addMessage(`Based on your ${symptom.toLowerCase()}, you might be experiencing:`, 'bot')
      selectedSymptom.conditions.forEach(condition => {
        addMessage(`- ${condition}`, 'bot')
      })
      addMessage("Possible medications or treatments include:", 'bot')
      selectedSymptom.medications.forEach(medication => {
        addMessage(`- ${medication}`, 'bot')
      })
      addMessage("Remember, this is not a substitute for professional medical advice. If symptoms persist or worsen, please consult a healthcare provider.", 'bot')
      addMessage("Would you like to check another symptom?", 'bot', ['Yes', 'No'])
    }
  }

  const handleOption = (option: string) => {
    addMessage(option, 'user')
    if (option === 'Yes') {
      addMessage("What other symptom are you experiencing?", 'bot', symptoms.map(s => s.name))
    } else {
      addMessage("Thank you for using the symptom checker. Take care and remember to consult a healthcare provider for personalized medical advice.", 'bot')
    }
  }

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle>Symptom Checker</CardTitle>
      </CardHeader>
      <CardContent>
        <ScrollArea className="h-[400px] pr-4">
          {messages.map((message) => (
            <div
              key={message.id}
              className={`mb-4 ${
                message.sender === 'user' ? 'text-right' : 'text-left'
              }`}
            >
              <div
                className={`inline-block p-2 rounded-lg ${
                  message.sender === 'user'
                    ? 'bg-primary text-primary-foreground'
                    : 'bg-secondary text-secondary-foreground'
                }`}
              >
                {message.text}
              </div>
              {message.options && (
                <div className="mt-2 space-y-2">
                  {message.options.map((option) => (
                    <Button
                      key={option}
                      variant="outline"
                      onClick={() => message.text.includes("symptom") ? handleSymptomSelection(option) : handleOption(option)}
                      className="mr-2"
                    >
                      {option}
                    </Button>
                  ))}
                </div>
              )}
            </div>
          ))}
        </ScrollArea>
      </CardContent>
      <CardFooter>
        <p className="text-sm text-muted-foreground">
          Disclaimer: This symptom checker is for educational purposes only and is not a substitute for professional medical advice, diagnosis, or treatment.
        </p>
      </CardFooter>
    </Card>
  )
}