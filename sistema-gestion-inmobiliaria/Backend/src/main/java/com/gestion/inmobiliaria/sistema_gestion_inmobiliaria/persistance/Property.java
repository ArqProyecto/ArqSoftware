package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private double price;
    @JsonProperty("isAvailable")
    private Boolean isAvailable;

    // Constructor vacío (requerido para frameworks de persistencia)
    public Property() {}

    // Constructor con parámetros
    public Property(String address, double price, Boolean isAvailable) {
        this.address = address;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }

}