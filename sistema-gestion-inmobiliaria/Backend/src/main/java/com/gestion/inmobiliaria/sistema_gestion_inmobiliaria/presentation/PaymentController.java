package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.presentation;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PaymentService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Payment;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.UserService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;  // Servicio para obtener el usuario por ID

    @Autowired
    private LeaseService leaseService;  // Servicio para obtener el contrato de arrendamiento por ID

    // Endpoint para procesar un pago
    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment paymentRequest) {
        try {
            // Obtener el usuario y el contrato de arrendamiento usando los IDs proporcionados en el body
            User user = userService.getUserById(paymentRequest.getUserId());
            Lease lease = leaseService.getLeaseById(paymentRequest.getLeaseId());

            if (user == null || lease == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 400 (Bad Request) si no se encuentran los objetos
            }

            // Establecer el usuario y el contrato de arrendamiento en el objeto Payment
            paymentRequest.setUser(user);
            paymentRequest.setLease(lease);

            // Procesar el pago utilizando el PaymentService
            Payment processedPayment = paymentService.processPayment(user.getId(), paymentRequest.getAmount(), paymentRequest.getPaymentMethod());
            return new ResponseEntity<>(processedPayment, HttpStatus.CREATED);  // Retorna el pago con código 201 (Creado)
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 400 (Bad Request) si ocurre algún error de validación
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 (Internal Server Error) si ocurre algún error inesperado
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