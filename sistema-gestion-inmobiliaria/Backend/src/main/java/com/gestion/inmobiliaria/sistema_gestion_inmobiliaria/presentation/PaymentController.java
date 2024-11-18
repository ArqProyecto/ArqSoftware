package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.presentation;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;  // Inyección del servicio de pagos

    // Endpoint para procesar un pago
    
    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestParam Long userId,
                                                  @RequestParam double amount,
                                                  @RequestParam String paymentMethod) {
        try {
            // Llamar al servicio de pagos para procesar el pago
            Payment payment = paymentService.processPayment(userId, amount, paymentMethod);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);  // Retornar pago con código 201 (Creado)
        } catch (IllegalArgumentException e) {
            // Manejo de error específico para IllegalArgumentException
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 400 (Bad Request)
        } catch (RuntimeException e) {
            // Manejo de error específico para RuntimeException
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 (Internal Server Error)
        }
    }


    // Endpoint para obtener un pago por su ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        try {
            Payment payment = paymentService.getPaymentById(paymentId);
            return ResponseEntity.ok(payment);  // Retorna el pago con código 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // 404 Not Found si el pago no existe
        }
    }
}
