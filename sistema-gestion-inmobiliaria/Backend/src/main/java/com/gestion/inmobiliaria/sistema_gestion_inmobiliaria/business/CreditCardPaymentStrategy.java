/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author Diego
 */
@Service
public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(User user, double amount) {
        // Lógica para procesar el pago con tarjeta de crédito
        System.out.println("Procesando el pago de " + amount + " con tarjeta de crédito.");
        return true; // Simulación de pago exitoso
    }
}

