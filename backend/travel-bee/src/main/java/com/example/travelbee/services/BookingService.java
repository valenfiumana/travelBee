package com.example.travelbee.services;

import com.example.travelbee.email.EmailSenderService;
import com.example.travelbee.entities.Booking;
import com.example.travelbee.entities.Product;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.BookingRepository;
import com.example.travelbee.security.entities.dto.UserDTO;
import com.example.travelbee.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    UserDetailsServiceImpl userService;
    @Autowired
    ProductService productService;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    //SAVE
    public Booking save(Booking booking) throws ResourceNotFoundException{
        if(booking.getCheckin().isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("The check In can't be before today");
        }
        else if(booking.getCheckin().isAfter(booking.getCheckout())){
            throw new ResourceNotFoundException("The check in can't be after the check out");
        }
        else if(booking.getCheckout().equals(null)||booking.getCheckin().equals(null)){
            throw new ResourceNotFoundException("There are some null fields");
        }
        else{
            return bookingRepository.save(booking);
        }
    }


    //FIND BY ID
    public Optional<Booking> findById(Long id) throws ResourceNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isPresent()){
            return booking;
        }
        else{
            throw new ResourceNotFoundException("There is no booking with id "+id);
        }
    }
    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }

    //DELETE
    public void delete(Long id) throws ResourceNotFoundException{
        Optional<Booking> category = bookingRepository.findById(id);
        if(category.isPresent()){
            bookingRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The booking with id "+id+" doesn't exist, so It could not be deleted");
        }
    }
    //FIND BY PRODUCT ID
    public List<Booking> findByProductId(Long id) throws ResourceNotFoundException {
        return bookingRepository.findByProductId(id);
    }


    // BOOKING EMAIL
    public void sendBookingMail (Long id) throws ResourceNotFoundException, MessagingException {
        Optional<Booking> booking = findById(id);
        UserDTO userDTO = userService.findUserById(booking.get().getUserId());
        Long productId = booking.get().getProductId();
        Optional<Product> product = productService.findById(productId);
        if (!product.isPresent()){
            throw new ResourceNotFoundException("There is no product with id "+productId);
        }
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", userDTO.getFirstName());
        templateModel.put("productName", product.get().getTitle());
        templateModel.put("dayIn", booking.get().getCheckin());
        templateModel.put("dayOut", booking.get().getCheckout());
        templateModel.put("checkIn", booking.get().getArrivalTime());
        emailSenderService.sendBookingEmail(userDTO.getEmail(), "Confirmación de reserva | Travel Bee", templateModel);
    }

    //FIND BY USER ID
    public List<Booking> findByUserId(Long id) throws ResourceNotFoundException {
        return bookingRepository.findByUserId(id);
    }


}
