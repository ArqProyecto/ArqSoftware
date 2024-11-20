/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.exceptions.AuthenticationException;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.exceptions.UserAlreadyExistsException;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Retorna el usuario autenticado
        }
        throw new AuthenticationException("Credenciales inválidas");
    }

    public void register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("El nombre de usuario ya está en uso");
        }
        userRepository.save(user); // Guarda el nuevo usuario
    }
    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));  // Lanza una excepción si no se encuentra el usuario
    }
}