/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import org.springframework.stereotype.Service;

@Service
public class BankTransferPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(User user, double amount) {
        // Lógica para procesar el pago con transferencia bancaria
        System.out.println("Procesando el pago de " + amount + " con transferencia bancaria.");
        // Simulación de pago exitoso
        return true;
    }
}

