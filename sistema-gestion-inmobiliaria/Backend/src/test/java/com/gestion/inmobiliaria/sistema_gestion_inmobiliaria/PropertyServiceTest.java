package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;


import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PropertyService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property;

    @BeforeEach
    void setUp() {
        // Inicialización de mocks
        MockitoAnnotations.openMocks(this);

        // Crear una propiedad de prueba
        property = new Property();
        property.setId(1L);
        property.setAddress("Calle Falsa 123");
        property.setPrice(150000);
        property.setAvailable(true);
    }

    @Test
    void testCreateProperty() {
        // Configurar mock para el repository
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        // Llamar al método del servicio
        Property createdProperty = propertyService.createProperty(property);

        // Verificar resultados
        assertNotNull(createdProperty);
        assertEquals("Calle Falsa 123", createdProperty.getAddress());
        assertEquals(150000, createdProperty.getPrice());
        assertTrue(createdProperty.isAvailable());

        // Verificar que el método save del repositorio fue llamado una vez
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testCreateProperty_InvalidAddress() {
        // Cambiar dirección a inválida
        property.setAddress("");

        // Verificar que se lanza la excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propertyService.createProperty(property);
        });
        assertEquals("La dirección no puede estar vacía", exception.getMessage());
    }

    @Test
    void testGetPropertyById() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Llamar al método del servicio
        Property foundProperty = propertyService.getPropertyById(1L);

        // Verificar resultados
        assertNotNull(foundProperty);
        assertEquals(1L, foundProperty.getId());
        assertEquals("Calle Falsa 123", foundProperty.getAddress());
    }

    @Test
    void testGetPropertyById_NotFound() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción cuando el inmueble no se encuentra
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propertyService.getPropertyById(1L);
        });
        assertEquals("Inmueble no encontrado", exception.getMessage());
    }

    @Test
    void testUpdateProperty() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        // Modificar propiedades
        property.setAddress("Nueva dirección");
        property.setPrice(200000);

        // Llamar al método del servicio
        Property updatedProperty = propertyService.updateProperty(1L, property);

        // Verificar resultados
        assertNotNull(updatedProperty);
        assertEquals("Nueva dirección", updatedProperty.getAddress());
        assertEquals(200000, updatedProperty.getPrice());

        // Verificar que el método save del repositorio fue llamado
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testDeleteProperty() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Llamar al método del servicio
        propertyService.deleteProperty(1L);

        // Verificar que el método delete del repositorio fue llamado
        verify(propertyRepository, times(1)).delete(any(Property.class));
    }
}
