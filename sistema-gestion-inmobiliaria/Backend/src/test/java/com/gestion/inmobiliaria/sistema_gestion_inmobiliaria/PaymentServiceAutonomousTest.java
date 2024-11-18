package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentContext;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PaymentRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Payment;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceAutonomousTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentContext paymentContext;

    @InjectMocks
    private PaymentService paymentService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock de un usuario
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
    }

    @Test
    @Description("Test para procesar un pago exitoso")
    @Step("Iniciar procesamiento de pago con éxito")
    public void testProcessPaymentSuccess() {
        // Arrange
        Long userId = user.getId();
        double amount = 100.0;
        String paymentMethod = "CREDIT_CARD";
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(paymentContext.executePayment(any(), eq(amount))).thenReturn(true);  // Suponemos que el pago es exitoso
        
        // Act
        Payment payment = paymentService.processPayment(userId, amount, paymentMethod);
        
        // Assert
        assertNotNull(payment);
        assertEquals(amount, payment.getAmount());
        assertEquals(paymentMethod, payment.getPaymentMethod());
        assertEquals("COMPLETED", payment.getStatus());
        
        // Verificar que el repositorio de pagos fue llamado
        verify(paymentRepository, times(1)).save(payment);

        // Paso para describir el estado final
        Allure.step("El pago fue procesado y guardado con éxito.");
    }

    @Test
    @Description("Test para procesar un pago cuando el usuario no existe")
    @Step("Intentar procesar pago para usuario no encontrado")
    public void testProcessPaymentUserNotFound() {
        // Arrange
        Long userId = 999L;  // Usuario inexistente
        double amount = 100.0;
        String paymentMethod = "CREDIT_CARD";
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(userId, amount, paymentMethod);
        });
        assertEquals("Usuario no encontrado", exception.getMessage());

        // Paso para describir el fallo
        Allure.step("El usuario no fue encontrado, se lanzó una excepción.");
    }

    @Test
    @Description("Test para procesar un pago con monto negativo")
    @Step("Intentar procesar pago con monto negativo")
    public void testProcessPaymentInvalidAmount() {
        // Arrange
        Long userId = user.getId();
        double amount = -50.0;  // Monto negativo
        String paymentMethod = "CREDIT_CARD";
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(userId, amount, paymentMethod);
        });
        assertEquals("El monto debe ser mayor que 0", exception.getMessage());

        // Paso para describir el fallo
        Allure.step("El monto es negativo, se lanzó una excepción.");
    }

    @Test
    @Description("Test para procesar un pago con método de pago inválido")
    @Step("Intentar procesar pago con método de pago inválido")
    public void testProcessPaymentInvalidPaymentMethod() {
        // Arrange
        Long userId = user.getId();
        double amount = 100.0;
        String paymentMethod = "INVALID_METHOD";  // Método de pago inválido
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(userId, amount, paymentMethod);
        });
        assertEquals("Método de pago no válido", exception.getMessage());

        // Paso para describir el fallo
        Allure.step("El método de pago es inválido, se lanzó una excepción.");
    }

    @Test
    @Description("Test para obtener un pago por ID con éxito")
    @Step("Obtener pago por ID con éxito")
    public void testGetPaymentByIdSuccess() {
        // Arrange
        Long paymentId = 1L;
        Payment payment = new Payment();
        payment.setId(paymentId);
        
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        
        // Act
        Payment result = paymentService.getPaymentById(paymentId);
        
        // Assert
        assertNotNull(result);
        assertEquals(paymentId, result.getId());

        // Paso para describir el resultado exitoso
        Allure.step("El pago fue encontrado con éxito por ID.");
    }

    @Test
    @Description("Test para obtener un pago por ID cuando no se encuentra")
    @Step("Intentar obtener pago por ID que no existe")
    public void testGetPaymentByIdNotFound() {
        // Arrange
        Long paymentId = 1L;
        
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(paymentId);
        });
        assertEquals("Payment not found", exception.getMessage());

        // Paso para describir el fallo
        Allure.step("No se encontró el pago por ID, se lanzó una excepción.");
    }
}