package com.example.travelbee.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="bookings")
public class Booking {
    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column
    private LocalDate checkin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column
    private LocalDate checkout;
    @Column
    private Long productId;
    @Column
    private Long userId;

    @JsonFormat(pattern="HH:mm:ss")
    @Column
    private Time arrivalTime;
    @Column
    private String cityOfOrigin;

    public Booking(){}

    public Booking(Long id, LocalDate checkin, LocalDate checkout, Long productId, Time arrivalTime, String cityOfOrigin) {
        this.id = id;
        this.checkin = checkin;
        this.checkout = checkout;
        this.arrivalTime = arrivalTime;
        this.cityOfOrigin = cityOfOrigin;
        this.productId = productId;

    }
}
