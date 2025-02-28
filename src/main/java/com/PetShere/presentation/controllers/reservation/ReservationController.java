package com.PetShere.presentation.controllers.reservation;

import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    @GetMapping
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getReservation() {
        // TODO: Obtener todas las reservas del cliente
        return null;
    }

    @GetMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        // TODO: Obtener una reserva por su id (validar en servicio que sea el cliente)
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> updateReservationFacture(@PathVariable Long id) {
        // TODO: Actualizar una reserva pra la factura
        return null;
    }
}
