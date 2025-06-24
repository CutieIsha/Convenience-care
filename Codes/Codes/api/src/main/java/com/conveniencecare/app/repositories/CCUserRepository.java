package com.conveniencecare.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conveniencecare.app.entities.CCUser;

@Repository
public interface CCUserRepository extends JpaRepository<CCUser,String> 
{
    
}
