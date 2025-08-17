package com.PetShere.persistence.repository.facture;

import com.PetShere.persistence.model.facture.Facture;
import com.PetShere.presentation.dto.facture.FactureDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByClientId(Long id);

    List<Facture> getFacturesByClientId(Long id);

    Boolean existsByIdAndClientId(Long id, Long id1);
}
