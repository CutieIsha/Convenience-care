package com.conveniencecare.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.conveniencecare.app.entities.CCPatientRecords;

import java.util.List;

public interface CCPatientRecordsRepository extends JpaRepository<CCPatientRecords, Long> {
    
    // Method to find health records by patient email
    List<CCPatientRecords> findByEmail(String email); 
}
