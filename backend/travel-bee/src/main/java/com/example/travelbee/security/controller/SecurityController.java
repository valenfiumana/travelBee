package com.example.travelbee.security.controller;

import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.security.entities.AuthenticationRequest;
import com.example.travelbee.security.entities.AuthenticationResponse;
import com.example.travelbee.security.entities.UserEntity;
import com.example.travelbee.security.entities.dto.UserDTO;
import com.example.travelbee.security.jwt.JwtUtil;
import com.example.travelbee.security.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@CrossOrigin(origins = "*")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtTokenProvider;


    @Operation(summary = "Log in with a registered account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user logged in successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationRequest.class))}),
            @ApiResponse(responseCode = "404", description = "There is no user with the entered credentials.",
                    content = @Content)})
    @PostMapping("/signin")
    public ResponseEntity<?> iniciarSesion(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            final UserEntity user = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getEmail());
            final String jwt = jwtTokenProvider.generateToken(user);
            UserDTO userDTO = userDetailsServiceImpl.findUserByEmail(authenticationRequest.getEmail());

            return ResponseEntity.ok(new AuthenticationResponse(jwt, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getId(), userDTO.getRole().getName()));

        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }



    @Operation(summary = "Register a new user account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user was successfully created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "409", description = "There are some null fields or the email was already used.",
                    content = @Content)})
    @PostMapping("/signup")
    public ResponseEntity<?> registrarse(@RequestBody UserDTO usuarioDTO) throws ResourceConflictException, MessagingException {
        boolean existsEmail = userDetailsServiceImpl.existsByEmail(usuarioDTO.getEmail());
        if(!existsEmail){
            return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsServiceImpl.saveUser(usuarioDTO));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The email is already used");
        }
    }

}