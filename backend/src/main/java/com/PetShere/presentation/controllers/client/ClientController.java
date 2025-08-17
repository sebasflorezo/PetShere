package com.PetShere.presentation.controllers.client;

import com.PetShere.persistence.model.facture.PaymentMethod;
import com.PetShere.presentation.dto.facture.FactureDto;
import com.PetShere.presentation.dto.pet.PetDto;
import com.PetShere.service.implementation.facture.FactureServiceImpl;
import com.PetShere.service.implementation.pet.PetServiceImpl;
import com.PetShere.service.implementation.user.UserServiceImpl;
import com.PetShere.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@PreAuthorize(Constants.CLIENT_AUTHORITY)
public class ClientController {

    private final PetServiceImpl petServiceImpl;
    private final FactureServiceImpl factureServiceImpl;
    private final UserServiceImpl userServiceImpl;

    // NOTE: No aplica diseño de api
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getClient() {
        return ResponseEntity.ok(userServiceImpl.getCurrentUser());
    }

    @GetMapping("/payments")
    public ResponseEntity<?> getPaymentMethods() {
        return ResponseEntity.ok(PaymentMethod.values());
    }

    @GetMapping("/{document}/pets")
    public ResponseEntity<?> getClientPets(@PathVariable String document) {
        List<PetDto> pets = petServiceImpl.getPetsByClientDocument(document);
        return ResponseEntity.ok(pets);
    }

    @PostMapping("/{document}/pets")
    public ResponseEntity<?> createClientPet(@PathVariable String document, @RequestBody PetDto petDto) {
        petDto.setOwnerDocument(document);
        URI location = ServletUriComponentsBuilder
                .fromPath("/pets")
                .path("/{id}")
                .buildAndExpand(petServiceImpl.createPet(petDto).getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{document}/factures")
    public ResponseEntity<?> getClientFactures(@PathVariable String document) {
        List<FactureDto> factures = factureServiceImpl.getFacturesByClientId(document);
        return ResponseEntity.ok(factures);
    }

    // @RequestBody JsonNode body
    @PostMapping("/{document}/factures")
    public ResponseEntity<?> createClientFacture(@PathVariable String document, @RequestBody JsonNode body) {
        URI location = ServletUriComponentsBuilder
                .fromPath("/factures")
                .path("/{id}")
                .buildAndExpand(factureServiceImpl.createFacture(document, body.get("paymentMethod").asText()))
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
