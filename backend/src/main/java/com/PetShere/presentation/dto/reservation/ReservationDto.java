package com.PetShere.presentation.dto.reservation;

import com.PetShere.persistence.model.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private Long factureId;
    private Long serviceId;
    private Long petId;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus reservationStatus;
    private Long price;
}
