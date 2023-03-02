package com.example.travelbee.security.entities.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class RoleDTO {
    private Long id;
    @NotNull
    private String name;

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public RoleDTO(){}
}
