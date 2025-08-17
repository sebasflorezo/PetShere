package com.PetShere.presentation.controllers.service;

import com.PetShere.presentation.dto.service.ServiceDto;
import com.PetShere.service.implementation.service.ServiceServiceImpl;
import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceServiceImpl serviceServiceImpl;

    @GetMapping
    @PreAuthorize(Constants.CLIENT_AUTHORITY + " of " + Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> getServices() {
        return ResponseEntity.ok(serviceServiceImpl.getServices());
    }

    @GetMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY + " of " + Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> getService(@PathVariable Long id) {
        return ResponseEntity.ok(serviceServiceImpl.getService(id));
    }

    @PostMapping
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> createService(@RequestBody ServiceDto serviceDto) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serviceServiceImpl.createService(serviceDto).getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        serviceServiceImpl.updateService(id, serviceDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/{state}")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> changeServiceState(@PathVariable Long id) {
        serviceServiceImpl.changeServiceState(id);
        return null;
    }
}
