package com.levelup.gamer.service;

import com.levelup.gamer.dto.*;
import com.levelup.gamer.model.User;
import com.levelup.gamer.repository.UserRepository;
import com.levelup.gamer.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // Login - replica la lógica del AuthContext
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        
        // Buscar usuario en BD (incluyendo admin)
        User user = userRepository.findByCorreo(email).orElse(null);
        
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(false, "Credenciales inválidas");
        }
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNombre(user.getNombre());
        userDTO.setCorreo(user.getCorreo());
        userDTO.setDescuento(user.getDescuento());
        userDTO.setEsAdmin(user.getEsAdmin());
        
        String token = jwtUtil.generateToken(user.getCorreo(), user.getEsAdmin());
        return new LoginResponse(true, false, userDTO, token);
    }
// Register - replica la lógica del AuthContext
    public RegisterResponse register(RegisterRequest request) {
        String correo = request.getCorreo().trim().toLowerCase();
        String run = request.getRun().trim();
        
        // Validar que el correo no exista
        if (userRepository.existsByCorreo(correo)) {
            return new RegisterResponse(false, "El correo ya está registrado", null, null, null);
        }
        
        // Validar RUN
        if (!run.matches("^\\d{1,9}[Kk]?$")) {
            return new RegisterResponse(false, "El RUN debe contener máximo 9 dígitos", null, null, null);
        }
        
        // Validar edad (mayor de 18)
        LocalDate fechaNacimiento = request.getFechaNacimiento();
        if (fechaNacimiento != null) {
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
            if (edad < 18) {
                return new RegisterResponse(false, "Debe ser mayor de 18 años", null, null, null);
            }
        }
        
        // Crear nuevo usuario
        User newUser = new User();
        newUser.setRun(run);
        newUser.setNombre(request.getNombre());
        newUser.setApellidos(request.getApellidos());
        newUser.setCorreo(correo);
        newUser.setPassword(request.getPassword());
        newUser.setFechaNacimiento(request.getFechaNacimiento());
        newUser.setRegion(request.getRegion());
        newUser.setComuna(request.getComuna());
        newUser.setDireccion(request.getDireccion());
        
        // Aplicar descuento DuocUC
        int descuento = 0;
        if (correo.endsWith("@duocuc.cl") || correo.endsWith("@profesor.duoc.cl")) {
            descuento = 20;
        }
        newUser.setDescuento(descuento);
        newUser.setEsAdmin(false);
        
        User savedUser = userRepository.save(newUser);
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setNombre(savedUser.getNombre());
        userDTO.setCorreo(savedUser.getCorreo());
        userDTO.setDescuento(savedUser.getDescuento());
        userDTO.setEsAdmin(savedUser.getEsAdmin());
        
        String token = jwtUtil.generateToken(savedUser.getCorreo(), false);
        return new RegisterResponse(true, null, descuento, userDTO, token);
    }
// Obtener todos los usuarios
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> {
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setNombre(user.getNombre());
                dto.setApellidos(user.getApellidos());
                dto.setCorreo(user.getCorreo());
                dto.setDescuento(user.getDescuento());
                dto.setEsAdmin(user.getEsAdmin());
                dto.setRegion(user.getRegion());
                dto.setComuna(user.getComuna());
                dto.setDireccion(user.getDireccion());
                return dto;
            })
            .collect(Collectors.toList());
    }
// Obtener usuario por ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setCorreo(user.getCorreo());
        dto.setDescuento(user.getDescuento());
        dto.setEsAdmin(user.getEsAdmin());
        dto.setRegion(user.getRegion());
        dto.setComuna(user.getComuna());
        dto.setDireccion(user.getDireccion());
        return dto;
    }
// Obtener usuario por correo
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByCorreo(email.trim().toLowerCase()).orElse(null);
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setCorreo(user.getCorreo());
        dto.setDescuento(user.getDescuento());
        dto.setEsAdmin(user.getEsAdmin());
        dto.setRegion(user.getRegion());
        dto.setComuna(user.getComuna());
        dto.setDireccion(user.getDireccion());
        return dto;
    }
}
