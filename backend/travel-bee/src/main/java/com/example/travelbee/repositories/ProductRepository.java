package com.example.travelbee.repositories;

import com.example.travelbee.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long category_id);

    Optional<Product> findByTitle (String title);

    
    @Query(value = "select * from products" +
            " where products.city_id = ?1 ", nativeQuery = true)
    List<Product> findByCityId(Long cityId);

    @Query(value = "select * from products" +
            " where products.id not in" +
            " (select distinct bookings.product_id" +
            " from bookings" +
            " where ((bookings.checkout > ?1 and bookings.checkin < ?2) or (bookings.checkout = ?1 and bookings.checkin = ?2))" +
            " or (bookings.checkin  between ?1 and ?2) " +
            " or (bookings.checkout  between ?1 and ?2))" +
            " group by products.id; ", nativeQuery = true)
    List<Product> filterByAvailableDates(LocalDate checkin, LocalDate checkout);

    @Query(value = "select * from products" +
            " where products.city_id = ?3" +
            " and products.id not in" +
            " (select distinct bookings.product_id" +
            " from bookings" +
            " where ((bookings.checkout > ?1 and bookings.checkin < ?2) or (bookings.checkout = ?1 and bookings.checkin = ?2))" +
            " or (bookings.checkin between ?1 and ?2) " +
            " or (bookings.checkout between ?1 and ?2))" +
            " group by products.id; ", nativeQuery = true)
    List<Product> filterByAvailableDatesAndCityId(LocalDate checkin, LocalDate checkout, Long cityId);
    // la Query tiene que ir con guión bajo (ej. city_id, product_id) pero en el resto de los archivos debe llevar camelCase.
    //Mysql transforma camelCase en "_"

}