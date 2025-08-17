package com.PetShere.presentation.dto.medicalHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryDto {
    private Long id;
    private Date consultationDate;
    private String description;
    private String treatment;
    private Long carerId;
    private Long petId;
}
