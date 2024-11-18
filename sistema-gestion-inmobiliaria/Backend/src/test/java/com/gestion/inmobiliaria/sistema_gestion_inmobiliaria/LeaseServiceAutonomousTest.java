package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.LeaseService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Property;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.LeaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeaseServiceAutonomousTest {

    @Mock
    private LeaseRepository leaseRepository;

    @InjectMocks
    private LeaseService leaseService;

    private Lease lease;
    private User tenant;
    private Property property;

    @BeforeEach
    public void setUp() {
        // Inicializamos los mocks
        MockitoAnnotations.openMocks(this);

        // Creamos datos de prueba
        tenant = new User();
        tenant.setId(1L);
        tenant.setUsername("Juan Pérez");

        property = new Property();
        property.setId(1L);
        property.setAddress("Calle 123");

        lease = new Lease(tenant, property, LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), 1000.0);
    }

    @Test
    public void testSaveLease() {
        when(leaseRepository.save(lease)).thenReturn(lease);

        Lease savedLease = leaseService.saveLease(lease);

        assertNotNull(savedLease);
        assertEquals(lease.getId(), savedLease.getId());
        verify(leaseRepository, times(1)).save(lease);  // Verifica que save se haya llamado una vez
    }

    @Test
    public void testGetAllLeases() {
        when(leaseRepository.findAll()).thenReturn(Arrays.asList(lease));

        var leases = leaseService.getAllLeases();

        assertNotNull(leases);
        assertEquals(1, leases.size());
        assertEquals(lease.getId(), leases.get(0).getId());
        verify(leaseRepository, times(1)).findAll();  // Verifica que findAll se haya llamado una vez
    }

    @Test
    public void testGetLeaseById() {
        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));

        Lease foundLease = leaseService.getLeaseById(1L);

        assertNotNull(foundLease);
        assertEquals(lease.getId(), foundLease.getId());
        verify(leaseRepository, times(1)).findById(1L);  // Verifica que findById se haya llamado una vez
    }

    @Test
    public void testGetLeaseByIdNotFound() {
        when(leaseRepository.findById(1L)).thenReturn(Optional.empty());

        Lease foundLease = leaseService.getLeaseById(1L);

        assertNull(foundLease);
        verify(leaseRepository, times(1)).findById(1L);  // Verifica que findById se haya llamado una vez
    }

    @Test
    public void testDeleteLeaseById() {
        doNothing().when(leaseRepository).deleteById(1L);

        leaseService.deleteLeaseById(1L);

        verify(leaseRepository, times(1)).deleteById(1L);  // Verifica que deleteById se haya llamado una vez
    }
}