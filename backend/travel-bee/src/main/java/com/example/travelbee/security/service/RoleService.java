package com.example.travelbee.security.service;

import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.Role;
import com.example.travelbee.security.entities.dto.RoleDTO;
import com.example.travelbee.security.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(RoleService.class);

    @Override
    public Role addRole(RoleDTO roleDTO) throws ResourceConflictException {
        if(roleDTO.getName().startsWith("ROLE_")){
            Role role = mapper.convertValue(roleDTO, Role.class);
            logger.info("Saving role "+ role.getName());
            return roleRepository.save(role);
        }
        else{
            throw new ResourceConflictException("The role name is required and it has to start with 'ROLE_'");
        }
    }

    @Override
    public Set<RoleDTO> listAllRoles() {
        List<Role> roles = roleRepository.findAll();
        Set<RoleDTO> rolesDto = new HashSet<>();
        for(Role role : roles){
            rolesDto.add(mapper.convertValue(role, RoleDTO.class));
        }
        logger.info("Roles listed correctly");
        return rolesDto;
    }

    @Override
    public void deleteRole(Long id) throws ResourceNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()){
            roleRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The role with id "+id+" does not exist");
        }
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) throws ResourceNotFoundException {
        if(roleRepository.findById(roleDTO.getId()).isEmpty()){
            throw new ResourceNotFoundException("The role with id "+roleDTO.getId()+" does not exist");
        }
        Role role = mapper.convertValue(roleDTO, Role.class);
        return mapper.convertValue(roleRepository.save(role), RoleDTO.class);
    }

    @Override
    public RoleDTO findRoleById(Long id) throws ResourceNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("There is no role with id "+id);
        }
        return mapper.convertValue(role, RoleDTO.class);
    }
}
