package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import org.springframework.stereotype.Component;

@Component
public class PaymentContext {

    private PaymentStrategy paymentStrategy; // Referencia a la estrategia de pago

    // Método para establecer la estrategia de pago
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Método para procesar el pago usando la estrategia configurada
    public boolean executePayment(User user, double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Estrategia de pago no configurada");
        }
        return paymentStrategy.processPayment(user, amount); // Llamar a la estrategia
    }
}
