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
@Table(name="cc_users")


public class CCUser {
    

    @Id
    String email;

    String username;

    String firstName;

    String lastName;

    String password;

    String phoneNo;

    String address;

    String role;
}

