package com.PetShere.presentation.dto.facture;

import com.PetShere.persistence.model.facture.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FactureDto {
    private Long id;
    private String clientDocument;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
}
