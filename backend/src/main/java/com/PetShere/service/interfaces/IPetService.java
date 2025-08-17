package com.PetShere.service.interfaces;

import com.PetShere.persistence.model.pet.Pet;
import com.PetShere.presentation.dto.pet.PetDto;

import java.util.List;

public interface IPetService {
    List<PetDto> getAllPets();

    PetDto getPetById(Long id);

    List<PetDto> getPetsByClientDocument(String document);

    Pet createPet(PetDto petDto);

    Pet updatePet(Long id, PetDto petDto);

    void changePetState(Long id);
}
