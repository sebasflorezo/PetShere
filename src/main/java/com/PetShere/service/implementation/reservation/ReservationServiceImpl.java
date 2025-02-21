package com.PetShere.service.implementation.reservation;

import com.PetShere.persistence.model.reservation.Reservation;
import com.PetShere.persistence.repository.reservation.IReservationRepository;
import com.PetShere.presentation.dto.reservation.ReservationDto;
import com.PetShere.service.interfaces.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    public List<ReservationDto> getReservationsByFactureId(Long id) {
        // TODO: Retornar las reservas de la factura
        // + Validar además que el cliente esté pidiendo sus reservas o retornar directamente sus reservas

        return List.of();
    }

    public Reservation createReservationForFacture(ReservationDto reservationDto) {
        // TODO: Crear una reserva para la factura ( id de factura )
        return null;
    }
}
