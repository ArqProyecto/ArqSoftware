package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.UserService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.exceptions.AuthenticationException;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.exceptions.UserAlreadyExistsException;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
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
    @Description("Prueba para autenticar al usuario con credenciales correctas.")
    @Step("Autenticar al usuario con nombre de usuario y contraseña válidos")
    public void testAuthenticateSuccess() {
        // Configura el comportamiento del mock
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Realiza la autenticación
        User authenticatedUser = userService.authenticate("testuser", "password123");

        // Verifica que el usuario autenticado no sea nulo y tenga el nombre correcto
        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
        
        // Verifica que el repositorio fue consultado una vez
        verify(userRepository, times(1)).findByUsername("testuser");
        
        // Registrar paso en Allure
        Allure.step("Autenticación exitosa con el usuario: testuser.");
    }

    @Test
    @Description("Prueba para autenticar al usuario con credenciales incorrectas.")
    @Step("Autenticar al usuario con credenciales incorrectas")
    public void testAuthenticateInvalidCredentials() {
        // Configura el comportamiento del mock
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Intenta autenticar con la contraseña incorrecta y espera una excepción
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            userService.authenticate("testuser", "wrongpassword");
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertEquals("Credenciales inválidas", exception.getMessage());
        
        // Verifica que el repositorio fue consultado una vez
        verify(userRepository, times(1)).findByUsername("testuser");
        
        // Registrar paso en Allure
        Allure.step("Error de autenticación debido a credenciales incorrectas.");
    }

    @Test
    @Description("Prueba para registrar un usuario que ya existe.")
    @Step("Intentar registrar un usuario con un nombre de usuario ya existente")
    public void testRegisterUserAlreadyExists() {
        // Configura el comportamiento del mock
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Intenta registrar al usuario y espera una excepción
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(user);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertEquals("El nombre de usuario ya está en uso", exception.getMessage());
        
        // Verifica que el repositorio fue consultado una vez
        verify(userRepository, times(1)).existsByUsername("testuser");
        
        // Registrar paso en Allure
        Allure.step("Error al registrar el usuario: El nombre de usuario ya está en uso.");
    }

    @Test
    @Description("Prueba para registrar un usuario exitosamente.")
    @Step("Registrar un usuario nuevo con éxito")
    public void testRegisterUserSuccess() {
        // Configura el comportamiento del mock
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Realiza el registro del usuario
        userService.register(user);

        // Verifica que los métodos del repositorio hayan sido llamados correctamente
        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, times(1)).save(user);
        
        // Registrar paso en Allure
        Allure.step("Usuario registrado exitosamente: testuser.");
    }
}