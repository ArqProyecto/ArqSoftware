package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PropertyService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PropertyRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
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
    @Description("Test para crear un nuevo inmueble con datos válidos.")
    @Step("Crear un nuevo inmueble")
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
        Allure.step("Inmueble creado exitosamente con la dirección: " + createdProperty.getAddress());
    }

    @Test
    @Description("Test cuando se intenta crear un inmueble con dirección inválida.")
    @Step("Intentar crear un inmueble con dirección vacía")
    void testCreateProperty_InvalidAddress() {
        // Cambiar dirección a inválida
        property.setAddress("");

        // Verificar que se lanza la excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propertyService.createProperty(property);
        });
        assertEquals("La dirección no puede estar vacía", exception.getMessage());
        Allure.step("Se lanzó excepción al intentar crear un inmueble con dirección vacía.");
    }

    @Test
    @Description("Test para obtener un inmueble por su ID.")
    @Step("Obtener un inmueble por su ID")
    void testGetPropertyById() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Llamar al método del servicio
        Property foundProperty = propertyService.getPropertyById(1L);

        // Verificar resultados
        assertNotNull(foundProperty);
        assertEquals(1L, foundProperty.getId());
        assertEquals("Calle Falsa 123", foundProperty.getAddress());
        Allure.step("Inmueble encontrado con ID: " + foundProperty.getId());
    }

    @Test
    @Description("Test cuando no se encuentra un inmueble por su ID.")
    @Step("Intentar obtener un inmueble por ID que no existe")
    void testGetPropertyById_NotFound() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción cuando el inmueble no se encuentra
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propertyService.getPropertyById(1L);
        });
        assertEquals("Inmueble no encontrado", exception.getMessage());
        Allure.step("No se encontró el inmueble con ID 1.");
    }

    @Test
    @Description("Test para actualizar los detalles de un inmueble.")
    @Step("Actualizar detalles de un inmueble")
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
        Allure.step("Inmueble actualizado correctamente con nueva dirección.");
    }

    @Test
    @Description("Test para eliminar un inmueble por su ID.")
    @Step("Eliminar inmueble por ID")
    void testDeleteProperty() {
        // Configurar mock para el repository
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Llamar al método del servicio
        propertyService.deleteProperty(1L);

        // Verificar que el método delete del repositorio fue llamado
        verify(propertyRepository, times(1)).delete(any(Property.class));
        Allure.step("Inmueble con ID 1 eliminado correctamente.");
    }
}