package com.example.travelbee.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String url;
    @Column
    private Long product_id;

    //constructors
    public Image(){}
    public Image(Long id, String title, String url, Long product_id) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.product_id = product_id;
    }


    //getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getproduct_id() {
        return product_id;
    }

    public void setproduct_id(Long product_id) {
        this.product_id = product_id;
    }
}
