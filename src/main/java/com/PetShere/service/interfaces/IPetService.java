package com.PetShere.service.interfaces;

import com.PetShere.presentation.dto.pet.PetDto;

import java.util.List;

public interface IPetService {
    List<PetDto> getAllPets();

    PetDto getPetById(Long id);

    List<PetDto> getPetsByOwnerDocument(String document);

    void createPet(PetDto petDto);

    void updatePet(Long id, PetDto petDto);

    void deletePet(Long id);
}
