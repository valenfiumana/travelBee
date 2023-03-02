package com.example.travelbee.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;
    @Column
    private Double price;
    @Column
    private Double longitude;
    @Column
    private Double latitude;
    @Column
    private String address;

    public Product() {
    }


    public Product(Long id, String title, String description, Double price, Double longitude, Double latitude, String address, Category category, City city, List<Feature> features, List<Policy> policies, List<Image> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.category = category;
        this.city = city;
        this.features = features;
        this.policies = policies;
        this.images = images;
    }

    public Product(Long id, String title, String description, Double price, Double longitude, Double latitude, String address, Category category, City city, List<Feature> features, List<Policy> policies) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.category = category;
        this.city = city;
        this.features = features;
        this.policies = policies;
    }

    //conection with category
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    //conection with cities
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId", referencedColumnName = "id", nullable=false)
    private City city;

//
    //conection with feature
    @ManyToMany(targetEntity = Feature.class)
    @JoinTable(
            name = "products_have_features",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private List<Feature> features;

    //conection with politics
    @ManyToMany(targetEntity = Policy.class)
    @JoinTable(
            name = "products_have_policies",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "politic_id"))
    private List<Policy> policies;

    //connection with image
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Image> images = new ArrayList<>();

    //connection with bookings
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<Booking> bookings = new ArrayList<>();


}

