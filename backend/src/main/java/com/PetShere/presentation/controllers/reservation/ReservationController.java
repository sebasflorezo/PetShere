package com.PetShere.presentation.controllers.reservation;

import com.PetShere.presentation.dto.reservation.ReservationDto;
import com.PetShere.service.implementation.reservation.ReservationServiceImpl;
import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationServiceImpl reservationServiceImpl;

    @GetMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationServiceImpl.getReservationById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> updateReservationFacture(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        reservationServiceImpl.updateReservation(id, reservationDto);
        return ResponseEntity.noContent().build();
    }
}
