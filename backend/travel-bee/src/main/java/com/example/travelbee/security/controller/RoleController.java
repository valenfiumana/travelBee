package com.example.travelbee.security.controller;

import com.example.travelbee.entities.City;
import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.Role;
import com.example.travelbee.security.entities.dto.RoleDTO;
import com.example.travelbee.security.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    RoleService roleService;

    //CREATE ROLE
    @Operation(summary = "Create a new role", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The role was created successfully.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The role is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDTO roleDTO) throws ResourceConflictException{
        return ResponseEntity.ok(roleService.addRole(roleDTO));
    }

    //FIND BY ID
    @Operation(summary = "Find a role by id", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The role with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The role with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findRoleById(@PathVariable Long id) throws ResourceNotFoundException{
        RoleDTO roleDTO = roleService.findRoleById(id);
        return ResponseEntity.ok(roleDTO);
    }

    //UPDATE
    @Operation(summary = "Update role", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The role was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))}),
            @ApiResponse(responseCode = "404", description = "The role with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The role is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO) throws ResourceNotFoundException{
        return ResponseEntity.ok(roleService.updateRole(roleDTO));
    }

    //DELETE
    @Operation(summary = "Delete role", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The role was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The role with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping
    public ResponseEntity<?> deleteRole(@PathVariable Long id) throws ResourceNotFoundException{
        RoleDTO roleDTO = roleService.findRoleById(id);
        if(roleDTO!=null){
            roleService.deleteRole(id);
            return ResponseEntity.status(HttpStatus.OK).body("Role with id "+id+" was deleted");
        }
        else{
            throw new ResourceNotFoundException("The role with id "+id+" was not found");
        }
    }

    //LIST ALL ROLES
    @Operation(summary = "List all roles", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The roles were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<RoleDTO>> listAll(){
        return ResponseEntity.ok(roleService.listAllRoles());
    }
}
