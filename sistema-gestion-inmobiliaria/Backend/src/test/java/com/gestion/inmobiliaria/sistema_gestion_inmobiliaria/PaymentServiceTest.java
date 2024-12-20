package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PaymentRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Payment;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentContext;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentStrategy;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentContext paymentContext;

    @Mock
    private PaymentStrategy paymentStrategy;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("John Doe");
    }

    @Test
    @Description("Test para procesar un pago con éxito")
    @Step("Procesar pago correctamente")
    public void testProcessPayment_Success() {
        // Arrange
        Long userId = 1L;
        double amount = 100.0;
        String paymentMethod = "CREDIT_CARD";

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(paymentContext.executePayment(user, amount)).thenReturn(true);

        // Act
        Payment payment = paymentService.processPayment(userId, amount, paymentMethod);

        // Assert
        assertNotNull(payment);
        assertEquals(amount, payment.getAmount());
        assertEquals("COMPLETED", payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        
        Allure.step("Pago procesado y guardado correctamente.");
    }

    @Test
    @Description("Test cuando el usuario no se encuentra en la base de datos")
    @Step("Buscar usuario en base de datos")
    public void testProcessPayment_UserNotFound() {
        // Arrange
        Long userId = 999L;  // User ID that does not exist
        double amount = 100.0;
        String paymentMethod = "CREDIT_CARD";

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(userId, amount, paymentMethod);
        });
        assertEquals("Usuario no encontrado", thrown.getMessage());
        
        Allure.step("No se encontró el usuario con el ID proporcionado.");
    }

    @Test
    @Description("Test cuando el monto del pago es inválido")
    @Step("Verificar monto del pago")
    public void testProcessPayment_InvalidAmount() {
        // Arrange
        Long userId = 1L;
        double amount = -100.0;  // Invalid amount
        String paymentMethod = "CREDIT_CARD";

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(userId, amount, paymentMethod);
        });
        assertEquals("El monto debe ser mayor que 0", thrown.getMessage());

        Allure.step("Monto de pago inválido.");
    }

    @Test
    @Description("Test cuando el método de pago es inválido")
    @Step("Verificar método de pago")
    public void testProcessPayment_InvalidPaymentMethod() {
        // Arrange
        Long userId = 1L;
        double amount = 100.0;
        String paymentMethod = "INVALID_METHOD";  // Invalid payment method

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(userId, amount, paymentMethod);
        });
        assertEquals("Método de pago no válido", thrown.getMessage());

        Allure.step("Método de pago inválido.");
    }

    @Test
    @Description("Test para obtener un pago por ID con éxito")
    @Step("Obtener pago por ID")
    public void testGetPaymentById_Success() {
        // Arrange
        Long paymentId = 1L;
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setAmount(100.0);
        payment.setStatus("COMPLETED");

        when(paymentRepository.findById(paymentId)).thenReturn(java.util.Optional.of(payment));

        // Act
        Payment foundPayment = paymentService.getPaymentById(paymentId);

        // Assert
        assertNotNull(foundPayment);
        assertEquals(paymentId, foundPayment.getId());
        assertEquals("COMPLETED", foundPayment.getStatus());

        Allure.step("Pago encontrado correctamente por ID.");
    }

    @Test
    @Description("Test cuando el pago no se encuentra por ID")
    @Step("Buscar pago por ID")
    public void testGetPaymentById_NotFound() {
        // Arrange
        Long paymentId = 999L;  // Payment ID that does not exist

        when(paymentRepository.findById(paymentId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(paymentId);
        });
        assertEquals("Payment not found", thrown.getMessage());

        Allure.step("No se encontró el pago con el ID proporcionado.");
    }
}