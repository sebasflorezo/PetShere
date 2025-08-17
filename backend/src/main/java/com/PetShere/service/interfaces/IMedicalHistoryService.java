package com.PetShere.service.interfaces;

import com.PetShere.persistence.model.medicalHistory.MedicalHistory;
import com.PetShere.presentation.dto.medicalHistory.MedicalHistoryDto;

import java.util.List;

public interface IMedicalHistoryService {
    List<MedicalHistoryDto> getAllMedicalHistories();

    List<MedicalHistoryDto> getAllMedicalHistoriesByPet(Long id);

    MedicalHistory createMedicalHistory(MedicalHistoryDto medicalHistoryDto);

    MedicalHistory updateMedicalHistory(Long id, MedicalHistoryDto medicalHistoryDto);
}
