/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.presentation;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.GestionInmobiliariaService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.GestionInmobiliariaServiceImpl;

public class GestionInmobiliariaServiceProxy implements GestionInmobiliariaService {
    private GestionInmobiliariaServiceImpl servicioReal;
    private boolean usuarioAutenticado;

    public GestionInmobiliariaServiceProxy() {
        this.servicioReal = new GestionInmobiliariaServiceImpl();
        this.usuarioAutenticado = false;
    }

    @Override
    public void autenticarUsuario(String username, String password) {
        // Validación de usuario
        if ("root".equals(username) && "12345..md".equals(password)) {
            usuarioAutenticado = true;
            System.out.println("Acceso concedido por el proxy.");
            servicioReal.autenticarUsuario(username, password);
        } else {
            System.out.println("Acceso denegado: credenciales inválidas.");
        }
    }

    @Override
    public void gestionarArriendo(int idArriendo) {
        // Verificación de autenticación previa
        if (usuarioAutenticado) {
            servicioReal.gestionarArriendo(idArriendo);
        } else {
            System.out.println("Acceso denegado: Usuario no autenticado.");
        }
    }
}