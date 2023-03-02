package com.example.travelbee.controller;

import com.example.travelbee.entities.Booking;
import com.example.travelbee.entities.Category;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.services.CategoryService;
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
@RequestMapping("/categories")
@Tag(name = "Categories")
public class CategoryController {
    public final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    //requests
    //SAVE
    @Operation(summary = "Create a new category", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was created succesfully.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The category is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public Category saveCategory(@RequestBody Category category) throws ResourceNotFoundException {
        if(category.getName().isEmpty()||category.getDescription().isEmpty()||category.getImageUrl().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else{
            return categoryService.save(category);
        }
    }

    //FIND BY ID
    @Operation(summary = "Find a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The category with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategory(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Category> category = categoryService.findById(id);
        if(category.isPresent()){
            return ResponseEntity.ok(category.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    //FIND ALL
    @Operation(summary = "List all categories", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The categories were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public List<Category> findAllCategories(){
        return categoryService.findAll();
    }

    //UPDATE
    @Operation(summary = "Update category", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "404", description = "The category with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The category is incomplete or with wrong data.",
                    content = @Content)})
    //----------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws ResourceNotFoundException{
        Optional<Category> categoryRequested = categoryService.findById(category.getId());
        if(categoryRequested.isPresent()){
            Category categoryUpdated = categoryService.update(category);
            return ResponseEntity.ok(categoryUpdated);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    //DELETE
    @Operation(summary = "Delete category", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The category with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Category> category = categoryService.findById(id);
        if(category.isPresent()){
            categoryService.delete(id);
            return ResponseEntity.ok("The category with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The category couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
}
