package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance;

import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lease_id", nullable = false) // Relación con el contrato de arrendamiento
    private Lease lease; // El pago está asociado a un contrato de arrendamiento

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Relación con el usuario que realiza el pago
    private User user; // El usuario que realiza el pago

    private double amount;
    private String paymentMethod; // Ejemplo: "Tarjeta", "Transferencia", etc.
    private String status; // Estado del pago (ejemplo: "Pendiente", "Completado")

    // Constructor vacío (requerido para frameworks de persistencia)
    public Payment() {}

    // Constructor con parámetros
    public Payment(Lease lease, User user, double amount, String paymentMethod, String status) {
        this.lease = lease;
        this.user = user;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}