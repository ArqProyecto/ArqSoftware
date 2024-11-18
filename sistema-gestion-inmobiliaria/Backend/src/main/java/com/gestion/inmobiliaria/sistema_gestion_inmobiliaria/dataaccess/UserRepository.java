/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    // Los métodos save, update y deleteById son proporcionados automáticamente por JpaRepository.
}
