package com.conveniencecare.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.conveniencecare.app.entities.CCUser;
import com.conveniencecare.app.model.LoginRequest;
import com.conveniencecare.app.model.LoginResponse;
import com.conveniencecare.app.model.ResponseData;
import com.conveniencecare.app.repositories.CCUserRepository;

@Service
public class CCUserService {

    @Autowired
    private CCUserRepository ccUserRepository;

    // Create User Method
    public ResponseEntity<?> createUser(CCUser ccUser) {
        try {
            CCUser savedCCUser = ccUserRepository.save(ccUser);
            return new ResponseEntity<>(new ResponseData<>("User created successfully", savedCCUser), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Not able to create a user", HttpStatus.CONFLICT);
        }
    }

    // Get All Users Method
    public ResponseEntity<?> getAllUsers() {
        List<CCUser> ccUsers = ccUserRepository.findAll();
        if (ccUsers.isEmpty()) {
            return new ResponseEntity<>("No users exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ccUsers, HttpStatus.OK);
        }
    }

    // Delete User Method
    public ResponseEntity<?> deleteUser(String email) {
        try {
            ccUserRepository.deleteById(email);
            return new ResponseEntity<>(new ResponseData<String>("User deleted successfully", email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseData<String>("Not able to delete the user", email), HttpStatus.CONFLICT);
        }
    }

    // Login Method
    public ResponseEntity<?> login(LoginRequest ccUser) {
        try {
            // Check if the user exists in the database
            Optional<CCUser> optionalCCUser = ccUserRepository.findById(ccUser.getEmail());
            if (optionalCCUser.isPresent()) {
                CCUser dbUser = optionalCCUser.get();
                
                // Compare the password (assumed here as plain-text, replace with secure hash comparison)
                if (ccUser.getPassword().equals(dbUser.getPassword())) {
                    // Return LoginResponse with user details (excluding sensitive info)
                    LoginResponse loginResponse = new LoginResponse(dbUser.getEmail(), dbUser.getUsername(),dbUser.getRole());
                    return new ResponseEntity<>(new ResponseData<>("Login successful", loginResponse), HttpStatus.OK);
                } else {
                    // Password incorrect
                    return new ResponseEntity<>(new ResponseData<>("Incorrect password", null), HttpStatus.UNAUTHORIZED);
                }
            } else {
                // User not found
                return new ResponseEntity<>(new ResponseData<>("User not found, please enter a valid email", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseData<>("An error occurred during login", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
