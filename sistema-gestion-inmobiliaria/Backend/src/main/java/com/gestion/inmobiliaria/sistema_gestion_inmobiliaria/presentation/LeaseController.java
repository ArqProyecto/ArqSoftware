package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.presentation;


import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.LeaseService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Lease;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @GetMapping
    public ResponseEntity<List<Lease>> getAllLeases() {
        List<Lease> leases = leaseService.getAllLeases();
        return ResponseEntity.ok(leases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lease> getLeaseById(@PathVariable Long id) {
        Lease lease = leaseService.getLeaseById(id);
        if (lease != null) {
            return ResponseEntity.ok(lease);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Lease> createLease(@RequestBody Lease lease) {
        Lease savedLease = leaseService.saveLease(lease);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLease);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaseById(@PathVariable Long id) {
        leaseService.deleteLeaseById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Arrendamiento eliminado con éxito");
    }
}
