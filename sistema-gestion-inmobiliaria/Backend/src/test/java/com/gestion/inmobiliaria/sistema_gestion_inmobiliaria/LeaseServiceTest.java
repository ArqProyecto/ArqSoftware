package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.LeaseService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.LeaseRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeaseServiceTest {

    @Mock
    private LeaseRepository leaseRepository;

    @InjectMocks
    private LeaseService leaseService;

    private Lease lease;
    private User tenant;
    private Property property;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicia los mocks

        tenant = new User();  // Crea el objeto User (asegúrate de configurarlo según tu modelo)
        property = new Property();  // Crea el objeto Property (asegúrate de configurarlo según tu modelo)

        // Crea el objeto Lease con un inquilino, propiedad, fechas y monto de renta
        lease = new Lease(tenant, property, LocalDate.now(), LocalDate.now().plusMonths(6), 1000.0);
        lease.setId(1L);  // Asegúrate de asignar un id válido
    }

    @Test
    public void testSaveLease() {
        // Simula el comportamiento de 'save' en el repositorio
        when(leaseRepository.save(any(Lease.class))).thenReturn(lease);

        // Llama al servicio para guardar el arrendamiento
        Lease savedLease = leaseService.saveLease(lease);

        // Verifica que el arrendamiento guardado no sea nulo y que el monto de renta sea correcto
        assertNotNull(savedLease);
        assertEquals(lease.getRentAmount(), savedLease.getRentAmount());

        // Verifica que el método save haya sido llamado una vez
        verify(leaseRepository, times(1)).save(lease);
    }

    @Test
    public void testGetAllLeases() {
        // Simula el comportamiento de 'findAll' en el repositorio
        List<Lease> leases = Arrays.asList(lease);
        when(leaseRepository.findAll()).thenReturn(leases);

        // Llama al servicio para obtener todos los arrendamientos
        List<Lease> allLeases = leaseService.getAllLeases();

        // Verifica que la lista no esté vacía y contiene el arrendamiento esperado
        assertNotNull(allLeases);
        assertEquals(1, allLeases.size());
        assertEquals(lease.getRentAmount(), allLeases.get(0).getRentAmount());

        // Verifica que el método findAll haya sido llamado una vez
        verify(leaseRepository, times(1)).findAll();
    }

    @Test
    public void testGetLeaseById() {
        // Simula el comportamiento de 'findById' en el repositorio
        when(leaseRepository.findById(anyLong())).thenReturn(Optional.of(lease));

        // Llama al servicio para obtener el arrendamiento por ID
        Lease foundLease = leaseService.getLeaseById(lease.getId());

        // Verifica que el arrendamiento encontrado no sea nulo y que el ID coincida
        assertNotNull(foundLease);
        assertEquals(lease.getId(), foundLease.getId());

        // Verifica que el método findById haya sido llamado una vez con el ID correcto
        verify(leaseRepository, times(1)).findById(lease.getId());
    }

    @Test
    public void testGetLeaseByIdNotFound() {
        // Simula el comportamiento de 'findById' cuando no se encuentra el arrendamiento
        when(leaseRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Llama al servicio para obtener el arrendamiento por ID que no existe
        Lease foundLease = leaseService.getLeaseById(99L);

        // Verifica que el arrendamiento encontrado sea nulo (no existe en la base de datos)
        assertNull(foundLease);

        // Verifica que el método findById haya sido llamado una vez con el ID 99
        verify(leaseRepository, times(1)).findById(99L);
    }

    @Test
    public void testDeleteLeaseById() {
        // Simula el comportamiento de 'deleteById' en el repositorio (sin hacer nada al eliminar)
        doNothing().when(leaseRepository).deleteById(anyLong());

        // Llama al servicio para eliminar el arrendamiento por ID
        leaseService.deleteLeaseById(lease.getId());

        // Verifica que el método deleteById haya sido llamado una vez con el ID correcto
        verify(leaseRepository, times(1)).deleteById(lease.getId());
    }
}