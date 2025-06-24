package com.conveniencecare.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conveniencecare.app.entities.CCPatientRecords; // Ensure this matches the package
import com.conveniencecare.app.services.CCPatientRecordsServices; // Ensure this matches the package

@RestController
@RequestMapping("/cc-patient-records")
@CrossOrigin(value = {"*"})
public class CCPatientRecordsController {

    @Autowired
    CCPatientRecordsServices ccPatientRecordsServices; // Service for managing patient health records

    // Endpoint to create a health record for a patient
    @PostMapping("/create-health-record")
    public ResponseEntity<?> createHealthRecord(@RequestBody CCPatientRecords healthRecord) {
        return ccPatientRecordsServices.createHealthRecord(healthRecord); // Pass CCPatientRecords object to the service
    }

    // Endpoint to get all health records for a specific patient
    @GetMapping("/get-health-records/{patientEmail}")
    public ResponseEntity<?> getHealthRecords(@PathVariable String patientEmail) {
        return ccPatientRecordsServices.getHealthRecordsByPatientEmail(patientEmail); // Retrieve health records for the patient
    }

    // Endpoint to delete a health record by its ID
    @DeleteMapping("/delete-health-record/{id}")
    public ResponseEntity<?> deleteHealthRecord(@PathVariable long id) {
        return ccPatientRecordsServices.deleteHealthRecord(id); // Pass ID to the service for deletion
    }
}
