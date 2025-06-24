package com.conveniencecare.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cc_patients")
public class CCPatient {

    @Id
    String email; // Unique identifier for the patient

    String firstName; // First name of the patient

    String lastName; // Last name of the patient

    String address; // Address of the patient

    String phoneNo; // Phone number of the patient

    String dob; // Date of birth of the patient (consider using LocalDate)

    String gender; // Gender of the patient

    int heightCm; // Height in centimeters

    String occupation; // Occupation of the patient

    String maritalStatus; // Marital status of the patient

    boolean isDisabled; // Indicates if the patient is disabled

    String companionName; // Name of the companion

    String companionRelationship; // Relationship of the companion

    String companionContactNo; // Contact number of the companion
}
