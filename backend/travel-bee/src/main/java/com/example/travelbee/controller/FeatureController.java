package com.example.travelbee.controller;

import com.example.travelbee.entities.Category;
import com.example.travelbee.entities.City;
import com.example.travelbee.entities.Feature;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.services.FeatureService;
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
@RequestMapping("/features")
public class FeatureController {
    private FeatureService featureService;

    @Autowired
    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    //SAVE
    @Operation(summary = "Create a new feature", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The feature was created successfully.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Feature.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The feature is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public Feature saveFeature(@RequestBody Feature feature) throws ResourceNotFoundException {
        if(feature.getTitle().isEmpty()||feature.getIcon_url().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else{
            return featureService.save(feature);
        }
    }

    //FIND BY ID
    @Operation(summary = "Find a feature by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The feature with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The feature with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Feature> findFeature(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Feature> feature = featureService.findById(id);
        return feature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //FIND ALL
    @Operation(summary = "List all features", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The features were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public List<Feature> findAllFeatures(){
        return featureService.findAll();
    }

    //UPDATE
    @Operation(summary = "Update feature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The feature was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Feature.class))}),
            @ApiResponse(responseCode = "404", description = "The feature with the provided id was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The feature is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Feature> updateFeature(@RequestBody Feature feature) throws ResourceNotFoundException{
        Optional<Feature> featureRequested = featureService.findById(feature.getId());
        if(featureRequested.isPresent()){
            Feature featureUpdated = featureService.update(feature);
            return ResponseEntity.ok(featureUpdated);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    //DELETE
    @Operation(summary = "Delete feature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The feature was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The feature with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeature(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Feature> feature = featureService.findById(id);
        if(feature.isPresent()){
            featureService.delete(id);
            return ResponseEntity.ok("The feature with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The feature couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
}
