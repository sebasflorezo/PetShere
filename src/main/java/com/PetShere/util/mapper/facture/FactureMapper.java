package com.PetShere.util.mapper.facture;

import com.PetShere.persistence.model.facture.Facture;
import com.PetShere.presentation.dto.facture.FactureDto;

public class FactureMapper {
    public static FactureDto toDto(Facture facture) {
        return FactureDto.builder()
                .id(facture.getId())
                .client_id(facture.getClient().getId())
                .build();
    }

    public static Facture toEntity(FactureDto factureDto) {
        return Facture.builder()
                .id(factureDto.getId())
                .build();
    }
}
