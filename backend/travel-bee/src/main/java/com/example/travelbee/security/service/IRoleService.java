package com.example.travelbee.security.service;

import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.Role;
import com.example.travelbee.security.entities.dto.RoleDTO;

import java.util.Set;

public interface IRoleService {
    //ADD
    Role addRole (RoleDTO roleDTO) throws ResourceConflictException;
    //LIST ALL
    Set<RoleDTO> listAllRoles();
    //DELETE
    void deleteRole(Long id) throws ResourceNotFoundException;
    //UPDATE
    RoleDTO updateRole(RoleDTO roleDTO) throws ResourceNotFoundException;
    //FIND BY ID
    RoleDTO findRoleById(Long id) throws ResourceNotFoundException;
}
