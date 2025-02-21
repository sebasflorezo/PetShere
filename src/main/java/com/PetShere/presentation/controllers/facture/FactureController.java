package com.PetShere.presentation.controllers.facture;

import com.PetShere.presentation.dto.reservation.ReservationDto;
import com.PetShere.service.implementation.facture.FactureServiceImpl;
import com.PetShere.service.implementation.reservation.ReservationServiceImpl;
import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureServiceImpl factureServiceImpl;
    private final ReservationServiceImpl reservationServiceImpl;

    @GetMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getFacture(@PathVariable Long id) {
        return ResponseEntity.ok(factureServiceImpl.getFactureById(id));
    }

    @GetMapping("/{id}/reservation")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getReservationsFacture(@PathVariable Long id) {
        List<ReservationDto> reservations = reservationServiceImpl.getReservationsByFactureId(id);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/{id}/reservation")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> createReservationFacture(@PathVariable Long id) {
        // TODO: Crear una reserva para la factura (id de factura)
        reservationServiceImpl.createReservationForFacture(null);
        return null;
    }
}
