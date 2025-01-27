package com.PetShere.service.implementation.pet;

import com.PetShere.presentation.dto.pet.PetDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.ResourceNotFoundException;
import com.PetShere.service.interfaces.IPetService;
import com.PetShere.util.mapper.pet.PetMapper;
import com.PetShere.persistence.model.pet.Pet;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.pet.IPetRespository;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.util.Constants;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements IPetService {

    private final IPetRespository petRespository;
    private final IUserRepository userRepository;

    @Override
    public List<PetDto> getAllPets() {
        return petRespository.findAll()
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetDto getPetById(Long id) {
        PetDto pet = petRespository.findById(id)
                .map(PetMapper::toDto)
                .filter(this::isPetOwnedByUser)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PET_NOT_FOUND_BY_ID + id));

        return (pet == null || !isPetOwnedByUser(pet)) ? null : pet;
    }

    @Override
    public List<PetDto> getPetsByOwnerDocument(String document) {
        Optional<User> user = AppUtil.getCurrentUser(userRepository);
        if (user.isEmpty() || !user.get().getDocument().equals(document))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        return petRespository.findByOwnerDocument(document)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PetDto> getPetsByOwnerDocument() {
        return AppUtil.getCurrentUser(userRepository)
                .map(User::getDocument)
                .map(petRespository::findByOwnerDocument)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPet(PetDto petDto) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));

        Validations.validatePetDto(petDto);
        Pet pet = PetMapper.toEntity(petDto);
        pet.setOwner(user);
        pet.setState(true);

        petRespository.save(pet);
    }

    @Override
    public void updatePet(Long id, PetDto petDto) {
        Pet pet = petRespository.findById(id)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));

        if (!isPetOwnedByUser(PetMapper.toDto(pet)))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        pet.setName(petDto.getName());
        pet.setSpecies(petDto.getSpecies());
        pet.setBreed(petDto.getBreed());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setWeight(petDto.getWeight());
        pet.setFoodPreferences(petDto.getFoodPreferences());

        petRespository.save(pet);
    }

    @Override
    public void deletePet(Long id) {
        Optional<User> user = AppUtil.getCurrentUser(userRepository);
        if (user.isEmpty() || !isPetOwnedByUser(id))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        petRespository.findById(id).ifPresent(p -> {
            p.setState(false);
            petRespository.save(p);
        });
    }

    private boolean isPetOwnedByUser(PetDto pet) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> petRespository.existsByIdAndOwnerDocument(pet.getId(), user.getDocument())
                ).orElse(false);
    }

    private boolean isPetOwnedByUser(Long id) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> petRespository.existsByIdAndOwnerDocument(id, user.getDocument())
                ).orElse(false);
    }
}
