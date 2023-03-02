package com.example.travelbee.security.service;

import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.UserEntity;
import com.example.travelbee.security.entities.dto.UserDTO;

import javax.mail.MessagingException;
import java.util.Set;

public interface IUserService {
    UserEntity saveUser(UserDTO userDTO) throws ResourceConflictException, MessagingException;
    UserDTO findUserById(Long id) throws ResourceNotFoundException;
    UserDTO findUserByEmail(String email) throws ResourceNotFoundException;
    Set<UserDTO> getUsers();
    boolean existsByEmail(String email);

    void deleteUser(Long id) throws ResourceNotFoundException;

    UserDTO updateUser(UserDTO userDTO) throws ResourceNotFoundException;
}
