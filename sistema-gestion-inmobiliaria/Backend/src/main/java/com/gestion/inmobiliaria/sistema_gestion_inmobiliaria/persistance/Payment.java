package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el contrato de arrendamiento (Solo mandamos el ID en el JSON)
    @ManyToOne
    @JoinColumn(name = "lease_id", nullable = false)
    @JsonIgnore  // Ignorar este campo cuando se serializa a JSON
    private Lease lease;

    // Relación con el usuario (Solo mandamos el ID en el JSON)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore  // Ignorar este campo cuando se serializa a JSON
    private User user;

    private double amount;
    private String paymentMethod;
    private String status;

    // Para serializar el 'userId' y 'leaseId' al JSON
    @JsonProperty("userId")  // Renombrar la propiedad para enviar solo el ID
    public Long getUserId() {
        return user != null ? user.getId() : null;  // Devuelve solo el ID del usuario
    }

    @JsonProperty("leaseId")  // Renombrar la propiedad para enviar solo el ID
    public Long getLeaseId() {
        return lease != null ? lease.getId() : null;  // Devuelve solo el ID del lease
    }

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