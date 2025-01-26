package app.PetShere.repositories.pet;

import app.PetShere.models.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRespository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerDocument(String ownerDocument);

    Boolean existsByIdAndOwnerDocument(Long id, String document);
}
