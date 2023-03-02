package com.example.travelbee.security.controller;

import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.dto.RoleDTO;
import com.example.travelbee.security.entities.dto.UserDTO;
import com.example.travelbee.security.repository.UserRepository;
import com.example.travelbee.security.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private UserRepository userRepository;


    //LIST ALL USERS
    @Operation(summary = "List all users", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The users were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public  ResponseEntity<Set<UserDTO>>findAll(){
        Set<UserDTO> usuarioDTOSet = userService.getUsers();
        return new ResponseEntity<>(usuarioDTOSet,HttpStatus.OK);
    }
    //DELETE
    @Operation(summary = "Delete user", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The user with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

    //FIND BY ID
    @Operation(summary = "Find a user by id", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The user with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO>findAccountById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        UserDTO userDTO=userService.findUserById(id);
        if(userDTO==null){
            throw new ResourceNotFoundException("There is no user with id " + id);
        }else{
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }

    //UPDATE
    @Operation(summary = "Update user", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "The user with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The user is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping("/update")
    public ResponseEntity<UserDTO>updateUser(
            @RequestBody UserDTO userDTO) throws ResourceNotFoundException {
        UserDTO updatedUser = userService.updateUser(userDTO);
        String URL = System.getenv().get("APP_HOST_FRONT");
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }
}
