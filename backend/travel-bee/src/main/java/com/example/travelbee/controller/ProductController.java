package com.example.travelbee.controller;

import com.example.travelbee.entities.Category;
import com.example.travelbee.entities.Policy;
import com.example.travelbee.entities.Product;
import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.dto.RoleDTO;
import com.example.travelbee.services.CategoryService;
import com.example.travelbee.services.CityService;
import com.example.travelbee.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    //POST
    @Operation(summary = "Create a new product", description = "To use this method you have to pass an admin JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The role was created successfully.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token. Note that the token must belong to an ADMIN.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The product is incomplete or with wrong data.",
                    content = @Content) })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) throws ResourceConflictException {
        return ResponseEntity.ok(productService.save(product));
    }
//
    //FIND BY ID
    @Operation(summary = "Find a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The product with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The product with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    //LIST ALL
    @Operation(summary = "List all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The products were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<Product>> listAll() {
        return ResponseEntity.ok(productService.listAll());
    }


    //GET RANDOM
    @Operation(summary = "Find all products and list them in a random order")
    @GetMapping("/random")
    public ResponseEntity<List<Product>> randomProducts() {
        List<Product> productsList = productService.listAll();
        Collections.shuffle(productsList);
        return ResponseEntity.ok(productsList);
    }

    //filter: CATEGORY ID
    @Operation(summary = "Find and list products by category id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The products were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The category with the provided id was not found",
                    content = @Content)})
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<?>> getAllProductsByCategoryId(@PathVariable Long categoryId) throws ResourceNotFoundException {
        if(categoryService.findById(categoryId).isPresent()){
            return ResponseEntity.ok(productService.filterByCategory(categoryId));
        }
        else{
            throw new ResourceNotFoundException("There is no category with id "+ categoryId);
        }

    }

    //filter: CITY ID
    @Operation(summary = "Find all products avaliable in the entered city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The products were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The city with the provided id was not found",
                    content = @Content)})
    @GetMapping("/filter/{cityId}")
    public ResponseEntity<List<?>> getAllProductsByCityId(@PathVariable Long cityId) throws ResourceNotFoundException{
        if(cityService.findById(cityId).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(productService.filterByCityId(cityId));
        }
        else{
            throw new ResourceNotFoundException("There is no city with id "+ cityId);
        }
    }

    //FILTER: AVAILABLE DATES
    @Operation(summary = "Find all products avaliable between given dates")
    @GetMapping("/filter/{checkin}/{checkout}")
    public ResponseEntity<List<Product>> getAllProductsByDates(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkin,
                                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkout) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.filterByDates(checkin, checkout));
    }

    //FILTER: AVAILABLE DATES IN SPECIFIC CITY
    @Operation(summary = "Find all products avaliable in the entered city and between given dates")
    @GetMapping("/filter/{checkin}/{checkout}/{cityId}")
    public ResponseEntity<List<Product>> getAllProductsByDatesAndCity(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkin,
                                                                      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkout,
                                                                      @PathVariable Long cityId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.filterByDatesAndCity(checkin, checkout, cityId));
    }

    //UPDATE
    @Operation(summary = "Update product", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The product was successfully updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))}),
            @ApiResponse(responseCode = "404", description = "The product with the provided id was not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The product is incomplete or with wrong data.",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws ResourceNotFoundException{
        Optional<Product> productRequested = productService.findById(product.getId());
        if(productRequested.isPresent()){
            Product productUpdated = productService.update(product);
            return ResponseEntity.ok(productUpdated);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    //DELETE
    @Operation(summary = "Delete product", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The product was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The product with the provided id was not found.",
                    content = @Content)})@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            productService.delete(id);
            return ResponseEntity.ok("The product with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
}