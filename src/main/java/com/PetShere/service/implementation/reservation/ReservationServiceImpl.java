package com.PetShere.service.implementation.reservation;

import com.PetShere.persistence.model.reservation.Reservation;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.facture.IFactureRepository;
import com.PetShere.persistence.repository.pet.IPetRepository;
import com.PetShere.persistence.repository.reservation.IReservationRepository;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.presentation.dto.facture.FactureDto;
import com.PetShere.presentation.dto.reservation.ReservationDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.interfaces.IReservationService;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Constants;
import com.PetShere.util.Validations;
import com.PetShere.util.mapper.reservation.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IUserRepository userRepository;
    private final IReservationRepository reservationRepository;
    private final IPetRepository petRepository;
    private final IFactureRepository factureRepository;

    public List<ReservationDto> getReservationsByFactureId(Long id) {
        if (!isFactureFromClient(id))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        return reservationRepository.findByFactureId(id)
                .stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Reservation createReservationForFacture(ReservationDto reservationDto) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));

        if (!isFactureFromClient(reservationDto.getFactureId()) || !isPetOwnedByClient(reservationDto.getPetId()))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        Validations.validateReservation(reservationDto);
        Reservation reservation = ReservationMapper.toEntity(reservationDto);
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
