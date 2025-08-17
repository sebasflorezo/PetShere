package com.PetShere.util.mapper.pet;

import com.PetShere.presentation.dto.pet.PetDto;
import com.PetShere.persistence.model.pet.Pet;

public class PetMapper {
    public static PetDto toDto(Pet pet) {
        return PetDto.builder()
                .id(pet.getId())
                .ownerDocument(pet.getOwner().getDocument())
                .name(pet.getName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .birthDate(pet.getBirthDate())
                .weight(pet.getWeight())
                .foodPreferences(pet.getFoodPreferences())
                .state(pet.getState())
                .build();
    }

    public static Pet toEntity(PetDto petDto) {
        return Pet.builder()
                .id(petDto.getId())
                .name(petDto.getName())
                .species(petDto.getSpecies())
                .breed(petDto.getBreed())
                .birthDate(petDto.getBirthDate())
                .weight(petDto.getWeight())
                .foodPreferences(petDto.getFoodPreferences())
                .state(petDto.getState())
                .build();
    }
}
