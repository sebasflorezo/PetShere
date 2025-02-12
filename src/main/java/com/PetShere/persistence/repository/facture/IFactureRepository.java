package com.PetShere.persistence.repository.facture;

import com.PetShere.persistence.model.facture.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByClientId(Long id);
}
