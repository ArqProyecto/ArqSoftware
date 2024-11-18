/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    
}
