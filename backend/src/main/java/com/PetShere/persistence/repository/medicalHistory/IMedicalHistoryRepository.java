package com.PetShere.persistence.repository.medicalHistory;

import com.PetShere.persistence.model.medicalHistory.MedicalHistory;
import com.PetShere.persistence.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByPetId(Long petId);
}
