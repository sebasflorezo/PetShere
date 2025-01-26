package app.PetShere.services.pet;

import app.PetShere.dtos.pet.PetDto;

import java.util.List;

public interface PetService {
    List<PetDto> getAllPets();

    PetDto getPetById(Long id);

    List<PetDto> getPetsByOwnerDocument(String document);

    void createPet(PetDto petDto);

    void updatePet(Long id, PetDto petDto);

    void deletePet(Long id);
}
