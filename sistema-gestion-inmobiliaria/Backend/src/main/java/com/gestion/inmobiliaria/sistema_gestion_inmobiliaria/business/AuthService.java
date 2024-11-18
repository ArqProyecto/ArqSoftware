/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;

public interface AuthService {
    User authenticate(String username, String password);
}