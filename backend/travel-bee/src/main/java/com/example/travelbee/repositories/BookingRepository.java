package com.example.travelbee.repositories;

import com.example.travelbee.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByProductId(Long productId);
    @Query(value = "select * from bookings" +
            " where bookings.user_id = ?1 ", nativeQuery = true)
    List<Booking> findByUserId(Long userId);
}
