package com.PetShere.service.implementation.medicalHistory;

import com.PetShere.persistence.model.medicalHistory.MedicalHistory;
import com.PetShere.persistence.model.pet.Pet;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.medicalHistory.IMedicalHistoryRepository;
import com.PetShere.persistence.repository.pet.IPetRepository;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.presentation.dto.medicalHistory.MedicalHistoryDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.NotFoundException;
import com.PetShere.service.interfaces.IMedicalHistoryService;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Constants;
import com.PetShere.util.mapper.medicalHistory.MedicalHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalHistoryImpl implements IMedicalHistoryService {

    private final IMedicalHistoryRepository medicalHistoryRepository;
    private final IPetRepository petRepository;
    private final IUserRepository userRepository;

    @Override
    public List<MedicalHistoryDto> getAllMedicalHistories() {
        return medicalHistoryRepository.findAll()
                .stream()
                .map(MedicalHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalHistoryDto> getAllMedicalHistoriesByPet(Long id) {
        return medicalHistoryRepository.findByPetId(id)
                .stream()
                .map(MedicalHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalHistory createMedicalHistory(MedicalHistoryDto medicalHistoryDto) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));

        Pet pet = petRepository.findById(medicalHistoryDto.getPetId())
                .orElseThrow(() -> new NotFoundException(Constants.PET_NOT_FOUND_BY_ID));

        MedicalHistory medicalHistory = MedicalHistoryMapper.toEntity(medicalHistoryDto);
        medicalHistory.setCarer(user);
        medicalHistory.setPet(pet);

        medicalHistoryRepository.save(medicalHistory);
        return medicalHistory;
    }

    @Override
    public MedicalHistory updateMedicalHistory(Long id, MedicalHistoryDto medicalHistoryDto) {
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_GENERIC));

        medicalHistory.setConsultationDate(medicalHistoryDto.getConsultationDate());
        medicalHistory.setDescription(medicalHistoryDto.getDescription());
        medicalHistory.setTreatment(medicalHistoryDto.getTreatment());

        medicalHistoryRepository.save(medicalHistory);
        return medicalHistory;
    }
}
