package com.PetShere.util.mapper.reservation;

import com.PetShere.persistence.model.reservation.Reservation;
import com.PetShere.presentation.dto.reservation.ReservationDto;

public class ReservationMapper {
    public static ReservationDto toDto(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .factureId(reservation.getFacture().getId())
                .serviceId(reservation.getService().getId())
                .petId(reservation.getPet().getId())
                .reservationStart(reservation.getReservationStart())
                .reservationEnd(reservation.getReservationEnd())
                .reservationStatus(reservation.getReservationStatus())
                .price(reservation.getPrice())
                .build();
    }

    public static Reservation toEntity(ReservationDto reservationDto) {
        return Reservation.builder()
                .id(reservationDto.getId())
                .reservationStart(reservationDto.getReservationStart())
                .reservationEnd(reservationDto.getReservationEnd())
                .reservationStatus(reservationDto.getReservationStatus())
                .price(reservationDto.getPrice())
                .build();
    }
}
