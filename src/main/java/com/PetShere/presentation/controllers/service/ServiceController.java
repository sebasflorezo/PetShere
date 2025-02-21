package com.PetShere.presentation.controllers.service;

import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    @GetMapping
    @PreAuthorize(Constants.CLIENT_AUTHORITY + " of " + Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> getServices() {
        // TODO: Retornar los servicios que se presten
        return null;
    }

    @PostMapping
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> createService() {
        // TODO: Crear un nuevo servicio
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> updateService(@PathVariable Long id) {
        // TODO: Actualizar un servicio por su id ( agregar body )
        return null;
    }

    @PatchMapping("/{id}/{state}")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> changeServiceState(@PathVariable Long id) {
        // TODO: Actualizar un servicio por su id ( state es string o boolean? )
        return null;
    }
}
