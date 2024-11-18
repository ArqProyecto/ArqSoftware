package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PropertyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    // Crear un nuevo inmueble
    public Property createProperty(Property property) {
        // Validación: verificar que la dirección no sea nula o vacía
        if (property.getAddress() == null || property.getAddress().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }

        // Validación: verificar que el precio sea positivo
        if (property.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        // Validación: verificar que la disponibilidad no sea nula
        if (property.isAvailable() == null) {
            throw new IllegalArgumentException("La disponibilidad del inmueble debe ser especificada");
        }

        // Guardar el inmueble en la base de datos
        return propertyRepository.save(property);
    }

    // Obtener un inmueble por su ID
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Inmueble no encontrado"));
    }
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    // Actualizar un inmueble existente
    public Property updateProperty(Long id, Property property) {
        Property existingProperty = getPropertyById(id);

        // Validación: verificar que la dirección no sea nula o vacía
        if (property.getAddress() == null || property.getAddress().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }

        // Validación: verificar que el precio sea positivo
        if (property.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        // Validación: verificar que la disponibilidad no sea nula
        if (property.isAvailable() == null) {
            throw new IllegalArgumentException("La disponibilidad del inmueble debe ser especificada");
        }

        existingProperty.setAddress(property.getAddress());
        existingProperty.setPrice(property.getPrice());
        existingProperty.setAvailable(property.isAvailable());

        return propertyRepository.save(existingProperty);
    }

    // Eliminar un inmueble
    public void deleteProperty(Long id) {
        Property property = getPropertyById(id);
        propertyRepository.delete(property);
    }
}