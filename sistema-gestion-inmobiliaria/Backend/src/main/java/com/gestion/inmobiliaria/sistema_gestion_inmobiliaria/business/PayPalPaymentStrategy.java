/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import org.springframework.stereotype.Service;

@Service
public class PayPalPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(User user, double amount) {
        // Lógica para procesar el pago con PayPal
        System.out.println("Procesando el pago de " + amount + " con PayPal.");
        // Simulación de pago exitoso
        return true;
    }
}

