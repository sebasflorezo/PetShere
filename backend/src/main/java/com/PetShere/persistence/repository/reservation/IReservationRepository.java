package com.PetShere.persistence.repository.reservation;

import com.PetShere.persistence.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByFactureId(Long id);
    List<Reservation> findByPetId(Long id);
}
