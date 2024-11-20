package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false) // Relación con la entidad User
    @JsonIgnore // Evita la serialización de la relación para prevenir ciclos
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false) // Relación con la entidad Property
    @JsonIgnore // Evita la serialización de la relación para prevenir ciclos
    private Property property;
    
    @OneToMany(mappedBy = "lease")
    @JsonIgnore // Evita la serialización de la lista para prevenir ciclos
    private List<Payment> payments;

    private LocalDate startDate;
    private LocalDate endDate;
    private double rentAmount;

    // Constructor vacío (requerido para frameworks de persistencia)
    public Lease() {}

    // Constructor con parámetros
    public Lease(User tenant, Property property, LocalDate startDate, LocalDate endDate, double rentAmount) {
        this.tenant = tenant;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentAmount = rentAmount;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    // Método auxiliar para calcular la duración del contrato en días
    public long getLeaseDurationDays() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    // Método auxiliar para verificar si el contrato aún está activo
    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return (startDate.isBefore(now) || startDate.isEqual(now)) &&
               (endDate.isAfter(now) || endDate.isEqual(now));
    }
}