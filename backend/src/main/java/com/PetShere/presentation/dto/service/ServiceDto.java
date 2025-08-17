package com.PetShere.presentation.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private Long id;
    private String name;
    private Long price;
    private Boolean state;
}
