package com.example.travelbee.security.service;

import com.example.travelbee.email.EmailSenderService;
import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.security.entities.Role;
import com.example.travelbee.security.entities.UserEntity;
import com.example.travelbee.security.entities.dto.RoleDTO;
import com.example.travelbee.security.entities.dto.UserDTO;
import com.example.travelbee.security.repository.RoleRepository;
import com.example.travelbee.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {
    //Implementa UserDetailsService para definir nuestra propio método loadUserbyUsername.
    // La interfaz UserDetailsService se usa para recuperar data relacionada a usuarios (GET)

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    //loadUserByUsername encuentra una entidad basada en el username pero puede personalizarse el proceso de encontrar al usuario.
    // Es usada por el DaoAuthenticationProvider para cargar datos del usuario en la autenticación
    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> usuarioBuscado = userRepository.findByEmail(username);
        if (usuarioBuscado.isPresent()) {
            return usuarioBuscado.get();
        } else {
            throw new UsernameNotFoundException("The mail entered is not registered");
        }
    }

    @Override
    public UserEntity saveUser(UserDTO usuarioDTO) throws ResourceConflictException, MessagingException {
        if(usuarioDTO.getFirstName()==null || usuarioDTO.getLastName()==null || usuarioDTO.getEmail()==null || usuarioDTO.getPassword()==null)
            throw  new ResourceConflictException("Intenta crear un usuario incompleto. Todos los datos son requeridos");
        //vemos si encontramos un usuario con el mismo email que el inresado en el formulario de registro
        Optional<UserEntity> usuarioEncontrado = userRepository.findByEmail(usuarioDTO.getEmail());

        if(usuarioEncontrado.isPresent()){
            throw new ResourceConflictException("El email ingresado ya fue registrado");
        }else{
            Role rolDefault = roleRepository.findRoleByName("ROLE_USER");
            if(usuarioDTO.getRole()==null && rolDefault!=null){
                usuarioDTO.setRole(mapper.convertValue(rolDefault, RoleDTO.class));
            }

            UserEntity usuario = new UserEntity(usuarioDTO.getFirstName(), usuarioDTO.getLastName(), usuarioDTO.getEmail(), new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()), mapper.convertValue(usuarioDTO.getRole(), Role.class));

            logger.info("The user with email " + usuarioDTO.getEmail() +" was succesfully added");

            //sending email
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("recipientName", usuario.getFirstName());
            emailSenderService.sendRegisterTemplate(usuario.getEmail(), "Confirmación de registro en Travel Bee", templateModel);
            return userRepository.save(usuario);
        }
    }

    @Override
    public Set<UserDTO> getUsers() {
        List<UserEntity> usuarios = userRepository.findAll();
        Set<UserDTO> usuarioDTO = new HashSet<>();

        for(UserEntity usuario : usuarios) {
            usuarioDTO.add(mapper.convertValue(usuario, UserDTO.class));
        }
        logger.info("Los usuarios se listaron correctamente");
        return usuarioDTO;
    }

    @Override
    public UserDTO findUserByEmail(String email) throws ResourceNotFoundException {
        UserDTO usuarioDTO;
        Optional<UserEntity> usuario = userRepository.findByEmail(email);

        if(usuario.isPresent()){
            usuarioDTO = mapper.convertValue(usuario, UserDTO.class);
        }else {
            throw new ResourceNotFoundException("No se encontró un usuario con email "+email);
        }

        return usuarioDTO;
    }

    @Override
    public UserDTO findUserById(Long id) throws ResourceNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        UserDTO userDTO;
        if (user.isPresent()) {
            userDTO = mapper.convertValue(user, UserDTO.class);
        }else{
            throw new ResourceNotFoundException("No se encontró un usuario con el id "+id);
        }
        return userDTO;
    }






    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteUser(Long id) throws ResourceNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()){
            roleRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The user with id "+id+" does not exist");
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws ResourceNotFoundException {
        if(userRepository.findById(userDTO.getId()).isEmpty()){
            throw new ResourceNotFoundException("The user with id "+userDTO.getId()+" does not exist");
        }
        UserEntity user = mapper.convertValue(userDTO, UserEntity.class);
        return mapper.convertValue(userRepository.save(user), UserDTO.class);
    }
}
