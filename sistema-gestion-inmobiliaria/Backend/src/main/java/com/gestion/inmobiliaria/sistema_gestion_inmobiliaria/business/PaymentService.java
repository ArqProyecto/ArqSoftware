package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PaymentRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Payment;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;  // Repositorio de pagos

    @Autowired
    private UserRepository userRepository;        // Repositorio de usuarios (para validar que el usuario existe)

    @Autowired
    private PaymentContext paymentContext;        // Contexto que maneja las estrategias de pago

    public Payment processPayment(Long userId, double amount, String paymentMethod) {
        // Paso 1: Validar que el usuario existe
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        User user = userOpt.get();

        // Paso 2: Validar que el monto es positivo
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }

        // Paso 3: Configurar la estrategia de pago en el contexto
        PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMethod);
        if (paymentStrategy == null) {
            throw new IllegalArgumentException("Método de pago no válido");
        }

        paymentContext.setPaymentStrategy(paymentStrategy);  // Establecer la estrategia en el contexto

        // Paso 4: Realizar el pago usando la estrategia a través del contexto
        boolean paymentSuccessful = paymentContext.executePayment(user, amount);
        if (!paymentSuccessful) {
            throw new RuntimeException("El pago no se pudo procesar");
        }

        // Paso 5: Registrar el pago en la base de datos
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("COMPLETED");

        paymentRepository.save(payment); // Guardar el pago en la base de datos

        return payment;
    }
    
    public Payment getPaymentById(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
    private PaymentStrategy getPaymentStrategy(String paymentMethod) {
        // Obtener la estrategia según el método de pago
        return switch (paymentMethod.toUpperCase()) {
            case "CREDIT_CARD" -> new CreditCardPaymentStrategy();
            case "PAYPAL" -> new PayPalPaymentStrategy();
            case "BANK_TRANSFER" -> new BankTransferPaymentStrategy();
            default -> null;
        }; // Método de pago no válido
    }
}