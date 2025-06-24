package com.conveniencecare.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.conveniencecare.app.entities.CCPatientRecords; // Ensure this matches the package
import com.conveniencecare.app.repositories.CCPatientRecordsRepository; // Ensure this matches the package

import java.util.List;

@Service
public class CCPatientRecordsServices {

    @Autowired
    CCPatientRecordsRepository ccPatientRecordsRepository; // Repository for accessing health records

    // Method to create a health record
    public ResponseEntity<?> createHealthRecord(CCPatientRecords healthRecord) {
        CCPatientRecords savedRecord = ccPatientRecordsRepository.save(healthRecord); // Save the health record
        return ResponseEntity.ok(savedRecord); // Return the saved record as response
    }

    public ResponseEntity<?> getHealthRecordsByPatientEmail(String patientEmail) {
    List<CCPatientRecords> records = ccPatientRecordsRepository.findByEmail(patientEmail);
    if (records.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No health records found.");
    }
    return ResponseEntity.ok(records);
}


    // Method to delete a health record by ID
    public ResponseEntity<?> deleteHealthRecord(long id) {
        if (ccPatientRecordsRepository.existsById(id)) { // Check if the record exists
            ccPatientRecordsRepository.deleteById(id); // Delete the record
            return ResponseEntity.ok("Health record deleted successfully."); // Return success message
        } else {
            return ResponseEntity.status(404).body("Health record not found."); // Return error if not found
        }
    }
}
