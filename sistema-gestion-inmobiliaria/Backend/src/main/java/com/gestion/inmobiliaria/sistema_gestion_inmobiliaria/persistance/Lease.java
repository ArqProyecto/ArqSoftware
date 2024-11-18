/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance;

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
    private User tenant; // El inquilino es una entidad User

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false) // Relación con la entidad Property
    private Property property; // La propiedad es una entidad Property
    
    @OneToMany(mappedBy = "lease")
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
