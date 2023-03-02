package com.example.travelbee.controller;

import com.example.travelbee.entities.Booking;
import com.example.travelbee.entities.Category;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.services.BookingService;
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

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //SAVE
    @Operation(summary = "Book an accomodation", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The booking was succesfully made",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The product or user id were not found.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The booking is incomplete or with wrong data.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) throws ResourceNotFoundException, MessagingException {
        bookingService.save(booking);
        bookingService.sendBookingMail(booking.getId());
        return ResponseEntity.ok(booking);
    }

    //FIND BY ID
    @Operation(summary = "Find a booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The booking with the id provided was successfully found.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The booking with the id provided was not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Booking> findBooking(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Booking> booking = bookingService.findById(id);
        if(booking.isPresent()){
            return ResponseEntity.ok(booking.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    //FIND ALL
    @Operation(summary = "List all bookings", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The bookings were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content)})
    @GetMapping
    public List<Booking> findAllBookings(){
        return bookingService.findAll();
    }

    //DELETE
    @Operation(summary = "Delete booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The booking was successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The booking with the provided id was not found.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Booking> booking = bookingService.findById(id);
        if(booking.isPresent()){
            bookingService.delete(id);
            return ResponseEntity.ok("The booking with id "+id+" has been deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The booking couldn't be deleted as the id "+
                    id+" does not exist");
        }

    }
    //FIND BY PRODUCT ID
    @Operation(summary = "Find and list bookings by product", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The bookings were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The product with the provided id was not found",
                    content = @Content)})
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<?>> getAllBookingsByProductId(@PathVariable Long productId) throws ResourceNotFoundException {
        return ResponseEntity.ok(bookingService.findByProductId(productId));
    }

    //FIND BY USER ID
    @Operation(summary = "Find and list bookings by user", description = "To use this method you have to pass a user JWT in header as a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The bookings were successfully listed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized. It happens when you send an invalid, expired or unexisting JWT token.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The user with the provided id was not found",
                    content = @Content)})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<?>> getAllBookingsByUserId(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(bookingService.findByUserId(userId));
    }
}
