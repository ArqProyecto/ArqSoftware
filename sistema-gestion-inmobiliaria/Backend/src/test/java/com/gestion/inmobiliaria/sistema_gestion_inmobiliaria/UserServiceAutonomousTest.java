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

public class UserServiceAutonomousTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
    }

    @Test
    public void testAuthenticateSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User authenticatedUser = userService.authenticate("testuser", "password123");

        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testAuthenticateInvalidCredentials() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            userService.authenticate("testuser", "wrongpassword");
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(user);
        });

        assertEquals("El nombre de usuario ya está en uso", exception.getMessage());
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    public void testRegisterUserSuccess() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        userService.register(user);

        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, times(1)).save(user);
    }
}