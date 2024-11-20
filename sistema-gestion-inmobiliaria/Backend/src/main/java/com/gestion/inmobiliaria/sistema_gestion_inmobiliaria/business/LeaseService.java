/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.LeaseRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PropertyRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import java.util.List;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public Lease saveLease(Lease lease) {
        if (lease.getTenant() == null || lease.getTenant().getId() == null) {
            throw new RuntimeException("El inquilino no puede ser nulo");
        }

        if (lease.getProperty() == null || lease.getProperty().getId() == null) {
            throw new RuntimeException("La propiedad no puede ser nula");
        }

        System.out.println("Tenant ID recibido: " + lease.getTenant().getId());
        System.out.println("Property ID recibido: " + lease.getProperty().getId());

        User tenant = userRepository.findById(lease.getTenant().getId())
            .orElseThrow(() -> new RuntimeException("Inquilino no encontrado"));
        System.out.println("Tenant encontrado: " + tenant.getId());

        Property property = propertyRepository.findById(lease.getProperty().getId())
            .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        System.out.println("Property encontrada: " + property.getId());

        lease.setTenant(tenant);
        lease.setProperty(property);

        return leaseRepository.save(lease);
    }


    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();  // Obtener todos los arrendamientos
    }

    public Lease getLeaseById(Long id) {
        return leaseRepository.findById(id).orElse(null);  // Obtener arrendamiento por ID
    }

    public void deleteLeaseById(Long id) {
        leaseRepository.deleteById(id);  // Eliminar arrendamiento por ID
    }
}