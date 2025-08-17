package com.PetShere.presentation.controllers.medicalHistory;

import com.PetShere.presentation.dto.medicalHistory.MedicalHistoryDto;
import com.PetShere.service.implementation.medicalHistory.MedicalHistoryImpl;
import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-histories")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryImpl medicalHistoryImpl;

    @PutMapping("/{id}")
    @PreAuthorize(Constants.CARER_AUTHORITY)
    public ResponseEntity<?> updateMedicalHistory(@PathVariable Long id, @RequestBody MedicalHistoryDto medicalHistoryDto) {
        try {
            medicalHistoryImpl.updateMedicalHistory(id, medicalHistoryDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }
}
