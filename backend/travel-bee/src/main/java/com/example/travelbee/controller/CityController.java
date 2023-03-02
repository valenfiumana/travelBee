package com.example.travelbee.controller;

import com.example.travelbee.entities.Category;
import com.example.travelbee.entities.City;
import com.example.travelbee.exceptions.GlobalExceptionHandler;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cities")
@Tag(name = "Cities")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    //SAVE
    @Operation(summary = "Create a new city", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The city was created successfully.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = City.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The city is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Object> cityRegister(@RequestBody City city) throws ResourceNotFoundException {
        return ResponseEntity.ok(cityService.save(city));
    }

    //LIST ALL
    @Operation(summary = "List all cities", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The cities were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<City>> listAll() {
        return ResponseEntity.ok(cityService.listAll());
    }

    //FIND BY ID
    @Operation(summary = "Find a city by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The city with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The city with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<City> city = cityService.findById(id);
        if(city.isPresent()){
            return ResponseEntity.ok(city.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //UPDATE
    @Operation(summary = "Update city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The city was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = City.class))}),
            @ApiResponse(responseCode = "404", description = "The city with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The city is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@RequestBody City city) throws ResourceNotFoundException{
        Optional<City> cityRequested = cityService.findById(city.getId());
        if(cityRequested.isPresent()){
            City cityUpdated = cityService.update(city);
            return ResponseEntity.ok(cityUpdated);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    //DELETE
    @Operation(summary = "Delete city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The city was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The city with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id) throws  ResourceNotFoundException {
        Optional<City> city = cityService.findById(id);
        if(city.isPresent()){
            cityService.delete(id);
            return ResponseEntity.ok("The city with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The city couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
}
