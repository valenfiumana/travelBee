package com.example.travelbee.controller;

import com.example.travelbee.entities.City;
import com.example.travelbee.entities.Policy;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.services.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/policies")
public class PolicyController {
    private PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    //SAVE
    @Operation(summary = "Create a new policy", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The policy was created successfully.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Policy.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The policy is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public Policy savePolicy(@RequestBody Policy policy) throws ResourceNotFoundException {
        if(policy.getDescription().isEmpty()||policy.getType().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else{
            return policyService.save(policy);
        }
    }

    //FIND BY ID
    @Operation(summary = "Find a policy by id", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The policy with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The policy with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Policy> findPolicy(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Policy> policy = policyService.findById(id);
        return policy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //FIND ALL
    @Operation(summary = "List all policies", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The policies were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public List<Policy> findAllPolicies(){
        return policyService.findAll();
    }

    //UPDATE
    @Operation(summary = "Update policy", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The policy was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Policy.class))}),
            @ApiResponse(responseCode = "404", description = "The policy with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The policy is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@RequestBody Policy policy) throws ResourceNotFoundException{
        Optional<Policy> policyRequested = policyService.findById(policy.getId());
        if(policyRequested.isPresent()){
            Policy policyUpdated = policyService.update(policy);
            return ResponseEntity.ok(policyUpdated);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    //DELETE
    @Operation(summary = "Delete policy", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The policy was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The policy with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Policy> policy = policyService.findById(id);
        if(policy.isPresent()){
            policyService.delete(id);
            return ResponseEntity.ok("The policy with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The policy couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
}
