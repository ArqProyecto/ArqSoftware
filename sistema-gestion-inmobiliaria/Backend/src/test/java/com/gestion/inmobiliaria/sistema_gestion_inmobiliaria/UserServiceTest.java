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
    @Description("Test de autenticación exitoso con credenciales válidas")
    @Step("Verificando que un usuario pueda autenticarse con las credenciales correctas")
    void testAuthenticate_Success() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        Allure.step("Usuario encontrado en el repositorio.");

        // Act
        User authenticatedUser = userService.authenticate("testuser", "password123");
        Allure.step("Usuario autenticado correctamente.");

        // Assert
        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
        Allure.step("Verificación de que el repositorio fue llamado una vez.");
    }

    @Test
    @Description("Test de autenticación fallida debido a una contraseña incorrecta")
    @Step("Verificando que la autenticación falle con una contraseña incorrecta")
    void testAuthenticate_Failure_InvalidPassword() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        Allure.step("Usuario encontrado en el repositorio.");

        // Act & Assert
        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> {
            userService.authenticate("testuser", "wrongpassword");
        });
        Allure.step("Excepción lanzada por contraseña incorrecta.");

        assertEquals("Credenciales inválidas", thrown.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        Allure.step("Verificación de que el repositorio fue llamado una vez.");
    }

    @Test
    @Description("Test de autenticación fallida debido a que el usuario no existe")
    @Step("Verificando que la autenticación falle cuando el usuario no se encuentra")
    void testAuthenticate_Failure_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        Allure.step("Usuario no encontrado en el repositorio.");

        // Act & Assert
        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> {
            userService.authenticate("testuser", "password123");
        });
        Allure.step("Excepción lanzada debido a usuario no encontrado.");

        assertEquals("Credenciales inválidas", thrown.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        Allure.step("Verificación de que el repositorio fue llamado una vez.");
    }

    @Test
    @Description("Test de registro exitoso cuando el nombre de usuario no existe")
    @Step("Verificando que el registro del usuario sea exitoso cuando el nombre de usuario no existe")
    void testRegister_Success() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        Allure.step("El nombre de usuario no existe en el repositorio.");

        // Act
        userService.register(user);
        Allure.step("Usuario registrado correctamente.");

        // Assert
        verify(userRepository, times(1)).save(user);
        Allure.step("Verificación de que el usuario fue guardado en el repositorio.");
    }

    @Test
    @Description("Test de fallo al registrar un usuario con un nombre de usuario ya existente")
    @Step("Verificando que el registro falle cuando el nombre de usuario ya existe")
    void testRegister_Failure_UserAlreadyExists() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        Allure.step("El nombre de usuario ya existe en el repositorio.");

        // Act & Assert
        UserAlreadyExistsException thrown = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(user);
        });
        Allure.step("Excepción lanzada debido a nombre de usuario ya existente.");

        assertEquals("El nombre de usuario ya está en uso", thrown.getMessage());
        verify(userRepository, times(0)).save(user);
        Allure.step("Verificación de que el repositorio no intentó guardar el usuario.");
    }
}