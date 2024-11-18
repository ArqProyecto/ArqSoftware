package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.UserService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.exceptions.AuthenticationException;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.exceptions.UserAlreadyExistsException;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
    }

    @Test
    void testAuthenticate_Success() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Act
        User authenticatedUser = userService.authenticate("testuser", "password123");

        // Assert
        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testAuthenticate_Failure_InvalidPassword() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Act & Assert
        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> {
            userService.authenticate("testuser", "wrongpassword");
        });

        assertEquals("Credenciales inválidas", thrown.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testAuthenticate_Failure_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        // Act & Assert
        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> {
            userService.authenticate("testuser", "password123");
        });

        assertEquals("Credenciales inválidas", thrown.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testRegister_Success() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(false);

        // Act
        userService.register(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegister_Failure_UserAlreadyExists() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        UserAlreadyExistsException thrown = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(user);
        });

        assertEquals("El nombre de usuario ya está en uso", thrown.getMessage());
        verify(userRepository, times(0)).save(user);
    }
}