package com.example.travelbee.security.entities;

import com.example.travelbee.entities.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name="last_name", nullable=false, length=25)
    private String lastName;

    @Column(nullable = false, unique = true, length=45)
    private String email;

    @NotNull
    @Size(min=7)
    @Column
    private String password;

    //relationship with role
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    //connection with bookings
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Booking> bookings = new ArrayList<>();

    public UserEntity(){}
    public UserEntity(Long id, String firstName, String lastname, String email, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public UserEntity(String firstName, String lastname, String email, String password,  Role role) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(getRole().getName());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
