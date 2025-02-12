package com.PetShere.util.mapper.service;

import com.PetShere.persistence.model.service.Service;
import com.PetShere.presentation.dto.service.ServiceDto;

public class ServiceMapper {
    public static ServiceDto toDto(Service service) {
        return ServiceDto.builder()
                .id(service.getId())
                .name(service.getName())
                .price(service.getPrice())
                .build();
    }

    public static Service toEntity(ServiceDto serviceDto) {
        return Service.builder()
                .id(serviceDto.getId())
                .name(serviceDto.getName())
                .price(serviceDto.getPrice())
                .build();
    }
}
