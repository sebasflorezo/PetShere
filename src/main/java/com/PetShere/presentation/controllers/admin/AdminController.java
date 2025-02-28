package com.PetShere.presentation.controllers.admin;

import com.PetShere.persistence.model.user.Role;
import com.PetShere.service.implementation.admin.AdminServiceImpl;
import com.PetShere.service.implementation.facture.FactureServiceImpl;
import com.PetShere.service.implementation.medicalHistory.MedicalHistoryImpl;
import com.PetShere.service.implementation.pet.PetServiceImpl;
import com.PetShere.service.implementation.service.ServiceServiceImpl;
import com.PetShere.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize(Constants.ADMIN_AUTHORITY)
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;
    private final MedicalHistoryImpl medicalHistoryImpl;
    private final PetServiceImpl petServiceImpl;
    private final FactureServiceImpl factureServiceImpl;
    private final ServiceServiceImpl serviceServiceImpl;

    @PostMapping("/change-user-role")
    public ResponseEntity<?> changeUserRole(@RequestBody JsonNode body) {
        try {
            Long userId = body.get("userId").asLong();
            String role = body.get("role").asText();
            adminServiceImpl.changeUserRole(userId, role);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getUserRoles() {
        return ResponseEntity.ok(Role.values());
    }

    @GetMapping("/test")
    public String testOnlyAdminRequest() {
        // Controlador para realizar pruebas ( solo admin )
        return "Only admin can see this";
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminServiceImpl.getUsers());
    }

    @GetMapping("/pets")
    public ResponseEntity<?> getPets() {
        return ResponseEntity.ok(petServiceImpl.getAllPets());
    }

    @GetMapping("/medical-history")
    public ResponseEntity<?> getMedicalHistory() {
        return ResponseEntity.ok(medicalHistoryImpl.getAllMedicalHistories());
    }

    @GetMapping("/factures")
    public ResponseEntity<?> getFactures() {
        return ResponseEntity.ok(factureServiceImpl.getAllFactures());
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getReservation() {
        // TODO: Obtener todas las reservas
        return null;
    }

    @GetMapping("/services")
    public ResponseEntity<?> getServices() {
        return ResponseEntity.ok(serviceServiceImpl.getServices());
    }
}
