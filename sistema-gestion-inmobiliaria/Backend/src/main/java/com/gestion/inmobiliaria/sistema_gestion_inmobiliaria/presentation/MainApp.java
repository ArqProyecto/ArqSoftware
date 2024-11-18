/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.presentation;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.GestionInmobiliariaService;

public class MainApp {
    public static void main(String[] args) {
        GestionInmobiliariaService servicio = new GestionInmobiliariaServiceProxy();

        // Intento de gestionar arriendo sin autenticación
        servicio.gestionarArriendo(101);

        // Autenticar usuario
        servicio.autenticarUsuario("admin", "password123");

        // Ahora que el usuario está autenticado, intenta gestionar arriendo
        servicio.gestionarArriendo(101);
    }
}

