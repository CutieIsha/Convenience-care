package com.conveniencecare.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conveniencecare.app.entities.CCPatient;

@Repository
public interface CCPatientRepository extends JpaRepository<CCPatient,String> 
{
    
}
