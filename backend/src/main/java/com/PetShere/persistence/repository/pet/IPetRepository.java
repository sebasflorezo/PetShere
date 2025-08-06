package com.PetShere.persistence.repository.pet;

import com.PetShere.persistence.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerDocument(String ownerDocument);

    Boolean existsByIdAndOwnerDocument(Long id, String document);
}
