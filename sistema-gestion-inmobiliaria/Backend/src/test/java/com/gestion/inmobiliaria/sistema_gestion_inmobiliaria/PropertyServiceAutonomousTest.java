package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.PropertyService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.PropertyRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceAutonomousTest {

    @Mock
    private PropertyRepository propertyRepository;  // Simulamos el repositorio

    @InjectMocks
    private PropertyService propertyService;  // Servicio bajo prueba

    private Property property;

    @BeforeEach
    void setUp() {
        // Inicializamos la propiedad con datos de prueba
        property = new Property();
        property.setId(1L);
        property.setAddress("123 Main St");
        property.setPrice(100000);  // Asegúrate de que el precio es positivo
        property.setAvailable(true);
    }

    @Test
    @Description("Test para crear una propiedad")
    @Step("Crear propiedad con los datos proporcionados")
    void testCreateProperty() {
        // Simulamos que el repositorio guarda la propiedad
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        // Ejecutamos el método
        Property createdProperty = propertyService.createProperty(property);

        // Verificamos los resultados
        assertNotNull(createdProperty);
        assertEquals(property.getAddress(), createdProperty.getAddress());
        assertEquals(property.getPrice(), createdProperty.getPrice());
        assertTrue(createdProperty.isAvailable());

        // Verificamos que el método save fue llamado
        verify(propertyRepository, times(1)).save(any(Property.class));

        // Añadimos un paso Allure para mayor contexto
        Allure.step("Propiedad creada correctamente con la dirección " + createdProperty.getAddress());
    }

    @Test
    @Description("Test para obtener una propiedad por su ID")
    @Step("Obtener propiedad por ID")
    void testGetPropertyById() {
        // Simulamos que el repositorio devuelve una propiedad
        when(propertyRepository.findById(property.getId())).thenReturn(java.util.Optional.of(property));

        // Ejecutamos el método
        Property foundProperty = propertyService.getPropertyById(property.getId());

        // Verificamos que los datos coinciden
        assertNotNull(foundProperty);
        assertEquals(property.getId(), foundProperty.getId());
        assertEquals(property.getAddress(), foundProperty.getAddress());
        assertEquals(property.getPrice(), foundProperty.getPrice());
        assertTrue(foundProperty.isAvailable());

        // Verificamos que el método findById fue llamado
        verify(propertyRepository, times(1)).findById(property.getId());

        // Añadimos un paso Allure para mayor contexto
        Allure.step("Propiedad encontrada con la dirección: " + foundProperty.getAddress());
    }
}