/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;
import java.util.List;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    public Lease saveLease(Lease lease) {
        return leaseRepository.save(lease);  // No necesitas implementar el método save
    }

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();  // No necesitas implementar findAll
    }

    public Lease getLeaseById(Long id) {
        return leaseRepository.findById(id).orElse(null);  // No necesitas implementar findById
    }

    public void deleteLeaseById(Long id) {
        leaseRepository.deleteById(id);  // No necesitas implementar deleteById
    }
}