package com.PetShere.service.implementation.reservation;

import com.PetShere.persistence.model.reservation.Reservation;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.facture.IFactureRepository;
import com.PetShere.persistence.repository.pet.IPetRepository;
import com.PetShere.persistence.repository.reservation.IReservationRepository;
import com.PetShere.persistence.repository.service.IServiceRepository;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.presentation.dto.reservation.ReservationDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.NotFoundException;
import com.PetShere.service.exception.ResourceNotFoundException;
import com.PetShere.service.interfaces.IReservationService;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Constants;
import com.PetShere.util.Validations;
import com.PetShere.util.mapper.reservation.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IUserRepository userRepository;
    private final IReservationRepository reservationRepository;
    private final IPetRepository petRepository;
    private final IFactureRepository factureRepository;
    private final IServiceRepository serviceRepository;

    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDto getReservationById(Long id) {
        ReservationDto reservation = reservationRepository.findById(id)
                .map(ReservationMapper::toDto)
                .filter(r -> isFactureFromClient(r.getFactureId()))
                .orElseThrow(() -> new ResourceNotFoundException(Constants.NOT_FOUND_GENERIC));

        return (reservation == null || !isFactureFromClient(reservation.getFactureId())) ? null : reservation;
    }

    public List<ReservationDto> getReservationsByFactureId(Long factureId) {
        if (!isFactureFromClient(factureId))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        return reservationRepository.findByFactureId(factureId)
                .stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Reservation createReservationForFacture(ReservationDto reservationDto) {
        if (!isFactureFromClient(reservationDto.getFactureId()) || !isPetOwnedByClient(reservationDto.getPetId()))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        Validations.validateReservation(reservationDto);
        Reservation reservation = ReservationMapper.toEntity(reservationDto);
        reservationRepository.save(reservation);

        return reservation;
    }

    public Reservation updateReservation(Long id, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_GENERIC));

        if (!isFactureFromClient(reservationDto.getFactureId()))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        Validations.validateReservation(reservationDto);
        reservation.setPet(
                petRepository.findById(reservationDto.getPetId())
                        .orElseThrow(() -> new NotFoundException(Constants.PET_NOT_FOUND_BY_ID))
        );
        reservation.setReservationStart(reservationDto.getReservationStart());
        reservation.setReservationEnd(reservationDto.getReservationEnd());
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        reservation.setService(
                serviceRepository.findById(reservationDto.getServiceId())
                        .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_GENERIC))
        );
        reservation.setPrice(reservationDto.getPrice());

        reservationRepository.save(reservation);
        return reservation;
    }

    private boolean isFactureFromClient(Long id) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> factureRepository.existsByIdAndClientId(id, user.getId()))
                .orElse(false);
    }

    private boolean isPetOwnedByClient(Long id) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> petRepository.existsByIdAndOwnerDocument(id, user.getDocument())
                ).orElse(false);
    }
}
