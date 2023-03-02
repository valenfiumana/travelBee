package com.example.travelbee.security.entities.dto;

import com.example.travelbee.security.entities.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 7, message = "La contraseña debe 7 o más caracteres")
    private String password;
    private RoleDTO role;

    public UserDTO(Long id, String firstName, String lastName, String email, String password, RoleDTO role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDTO(String firstName, String lastName, String email, String password, RoleDTO role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDTO() {
    }
}
