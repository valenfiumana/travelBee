package com.example.travelbee.security.entities;

import lombok.Getter;

@Getter
public class AuthenticationResponse {


    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String rol;
    private final String jwt;

    public AuthenticationResponse(String jwt, String firstName, String lastName, String email, Long userId,  String rol) {
        this.jwt = jwt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
        this.rol = rol;

    }
}