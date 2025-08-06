package com.PetShere.util.mapper.medicalHistory;

import com.PetShere.persistence.model.medicalHistory.MedicalHistory;
import com.PetShere.presentation.dto.medicalHistory.MedicalHistoryDto;

public class MedicalHistoryMapper {
    public static MedicalHistoryDto toDto(MedicalHistory medicalHistory) {
        return MedicalHistoryDto.builder()
                .id(medicalHistory.getId())
                .consultationDate(medicalHistory.getConsultationDate())
                .description(medicalHistory.getDescription())
                .treatment(medicalHistory.getTreatment())
                .carerId(medicalHistory.getCarer().getId())
                .petId(medicalHistory.getPet().getId())
                .build();
    }

    public static MedicalHistory toEntity(MedicalHistoryDto medicalHistoryDto) {
        return MedicalHistory.builder()
                .id(medicalHistoryDto.getId())
                .consultationDate(medicalHistoryDto.getConsultationDate())
                .description(medicalHistoryDto.getDescription())
                .treatment(medicalHistoryDto.getTreatment())
                .build();
    }
}
