package com.conveniencecare.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conveniencecare.app.services.CCUserService; // Ensure this matches the package
import com.conveniencecare.app.entities.CCUser; // Ensure this matches the package
import com.conveniencecare.app.model.LoginRequest;

@RestController
@RequestMapping("/cc-user-controller")
@CrossOrigin(value = {"*"})
public class CCUserController {
    
    @Autowired
    CCUserService ccUserService; // Make sure this matches

    @PostMapping("/create-User")
    public ResponseEntity<?> createUser(@RequestBody CCUser ccUser) {
        return ccUserService.createUser(ccUser);
    }

    @GetMapping("/get-All-Users")
    public ResponseEntity<?> getAllUsers() {
        return ccUserService.getAllUsers();
    }

    @DeleteMapping("/delete-User/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        return ccUserService.deleteUser(email);
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest ccUser) {
        return ccUserService.login(ccUser);
    }
}
