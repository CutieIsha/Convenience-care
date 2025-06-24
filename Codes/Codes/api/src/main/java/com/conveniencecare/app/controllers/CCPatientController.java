package com.conveniencecare.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conveniencecare.app.services.CCPatientService; // Ensure this matches the package
import com.conveniencecare.app.entities.CCPatient; // Ensure this matches the package

@RestController
@RequestMapping("/cc-patient-controller")
public class CCPatientController {

    @Autowired
    CCPatientService ccPatientService; // Assuming your service is called CCPatientService

    // Endpoint to create a patient
    @PostMapping("/create-patient")
    public ResponseEntity<?> createPatient(@RequestBody CCPatient ccPatient) {
        return ccPatientService.createPatient(ccPatient); // Pass CCPatient object to the service
    }

    // Endpoint to get all patients
    @GetMapping("/get-all-patients")
    public ResponseEntity<?> getAllPatients() {
        return ccPatientService.getAllPatients(); // Adjust method path and capitalization
    }

    // Endpoint to delete a patient by email
    @DeleteMapping("/delete-patient/{email}")
    public ResponseEntity<?> deletePatient(@PathVariable String email) {
        return ccPatientService.deletePatient(email);
    }
}
