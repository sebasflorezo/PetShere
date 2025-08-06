package com.PetShere.presentation.controllers.pet;

import com.PetShere.presentation.dto.medicalHistory.MedicalHistoryDto;
import com.PetShere.presentation.dto.pet.PetDto;
import com.PetShere.service.implementation.medicalHistory.MedicalHistoryImpl;
import com.PetShere.service.implementation.pet.PetServiceImpl;
import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetServiceImpl petServiceImpl;
    private final MedicalHistoryImpl medicalHistoryImpl;

    @GetMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getPet(@PathVariable Long id) {
        return ResponseEntity.ok(petServiceImpl.getPetById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
        petServiceImpl.updatePet(id, petDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> changePetState(@PathVariable Long id) {
        petServiceImpl.changePetState(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/medical-histories")
    @PreAuthorize(Constants.CARER_AUTHORITY)
    public ResponseEntity<?> getMedicalHistoriesByPet(@PathVariable Long id) {
        List<MedicalHistoryDto> medicalHistories = medicalHistoryImpl.getAllMedicalHistoriesByPet(id);
        return ResponseEntity.ok(medicalHistories);
    }

    @PostMapping("/{id}/medical-histories")
    @PreAuthorize(Constants.CARER_AUTHORITY)
    public ResponseEntity<?> createMedicalHistory(@PathVariable Long id, @RequestBody MedicalHistoryDto medicalHistoryDto) {
        medicalHistoryDto.setPetId(id);

        URI location = ServletUriComponentsBuilder
                .fromPath("/medical-histories")
                .path("/{id}")
                .buildAndExpand(medicalHistoryImpl.createMedicalHistory(medicalHistoryDto).getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
