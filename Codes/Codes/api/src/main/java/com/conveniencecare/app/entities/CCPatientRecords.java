package com.conveniencecare.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient_health_records ") 
public class CCPatientRecords {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    @Column(name="patient_email")
    String email; // Unique identifier for the patient

    String healthCondition; // Health condition of the patient

    String treatment; // Treatment provided

    String medication; // Medications prescribed

    String dosage; // Dosage of the medication

    String startDate; // Start date of the treatment/medication

    String endDate; // End date of the treatment/medication

    String followUpDate; // Date for follow-up appointment

    String doctorName; // Name of the doctor

    String clinicName; // Name of the clinic

    String allergies; // Allergies of the patient

    String notes; // Additional notes regarding the patient's health

    // Removed personal details
}
