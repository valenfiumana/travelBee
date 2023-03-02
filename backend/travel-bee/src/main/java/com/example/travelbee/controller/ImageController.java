package com.example.travelbee.controller;

import com.example.travelbee.entities.Category;
import com.example.travelbee.entities.City;
import com.example.travelbee.entities.Image;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.services.ImageService;
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
@RequestMapping("/images")
public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //SAVE
    @Operation(summary = "Create a new image", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The image was created successfully.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The image is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Object> imageRegister(@RequestBody Image image) throws ResourceNotFoundException {
        return ResponseEntity.ok(imageService.save(image));
    }

    //LIST ALL
    @Operation(summary = "List all images", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The images were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<Image>> listAll() {
        return ResponseEntity.ok(imageService.listAll());
    }

    //FIND BY ID
    @Operation(summary = "Find a image by id", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The image with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The image with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Image> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Image> image = imageService.findById(id);
        if(image.isPresent()){
            return ResponseEntity.ok(image.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //UPDATE
    @Operation(summary = "Update image", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The image was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class))}),
            @ApiResponse(responseCode = "404", description = "The image with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The image is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@RequestBody Image image) throws ResourceNotFoundException{
        Optional<Image> imageRequested = imageService.findById(image.getId());
        if(imageRequested.isPresent()){
            Image imageUpdated = imageService.update(image);
            return ResponseEntity.ok(imageUpdated);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    //DELETE
    @Operation(summary = "Delete image", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The image was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The image with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) throws  ResourceNotFoundException {
        Optional<Image> image = imageService.findById(id);
        if(image.isPresent()){
            imageService.delete(id);
            return ResponseEntity.ok("The image with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The image couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
}
