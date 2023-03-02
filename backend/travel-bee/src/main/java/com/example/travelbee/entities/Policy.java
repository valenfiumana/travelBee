package com.example.travelbee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String type;
    @Column
    private String description;

    public Policy(){}

    public Policy(Long id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }


}
