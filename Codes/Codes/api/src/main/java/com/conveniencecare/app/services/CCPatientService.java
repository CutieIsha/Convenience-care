package com.conveniencecare.app.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.conveniencecare.app.entities.CCPatient;
import com.conveniencecare.app.model.ResponseData;
import com.conveniencecare.app.repositories.CCPatientRepository;

@Service
public class CCPatientService {

    @Autowired
    CCPatientRepository ccPatientRepository; // Changed repository name

    // Method to create a new patient
    public ResponseEntity<?> createPatient(CCPatient ccPatient) {
        try {
            CCPatient savedCCPatient = ccPatientRepository.save(ccPatient); // Save CCPatient object
            return new ResponseEntity<>(new ResponseData<>("Patient created successfully", savedCCPatient), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Not able to create a patient", HttpStatus.CONFLICT);
        }
    }

    // Method to retrieve all patients
    public ResponseEntity<?> getAllPatients() {
        List<CCPatient> ccPatients = ccPatientRepository.findAll(); // Changed CCUser to CCPatient
        if (ccPatients.isEmpty()) {
            return new ResponseEntity<>("No patients exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ccPatients, HttpStatus.OK); // Removed unnecessary variable
        }
    }

    // Method to delete a patient by email
    public ResponseEntity<?> deletePatient(String email) {
        try {
            ccPatientRepository.deleteById(email); // Delete patient by email
            return new ResponseEntity<>(new ResponseData<String>("Patient deleted successfully", email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseData<String>("Not able to delete the patient", email), HttpStatus.CONFLICT);
        }
    }
}
