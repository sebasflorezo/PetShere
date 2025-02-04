package com.PetShere.service.implementation.pet;

import com.PetShere.presentation.dto.pet.PetDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.NotFoundException;
import com.PetShere.service.exception.ResourceNotFoundException;
import com.PetShere.service.interfaces.IPetService;
import com.PetShere.util.mapper.pet.PetMapper;
import com.PetShere.persistence.model.pet.Pet;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.pet.IPetRepository;
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

    private final IPetRepository petRepository;
    private final IUserRepository userRepository;

    @Override
    public List<PetDto> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetDto getPetById(Long id) {
        PetDto pet = petRepository.findById(id)
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

        return petRepository.findByOwnerDocument(document)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PetDto> getPetsByOwnerDocument() {
        return AppUtil.getCurrentUser(userRepository)
                .map(User::getDocument)
                .map(petRepository::findByOwnerDocument)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Pet createPet(PetDto petDto) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));

        Validations.validatePetDto(petDto);
        Pet pet = PetMapper.toEntity(petDto);
        pet.setOwner(user);
        pet.setState(true);

        petRepository.save(pet);
        return pet;
    }

    @Override
    public Pet updatePet(Long id, PetDto petDto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.PET_NOT_FOUND_BY_ID));

        if (!isPetOwnedByUser(PetMapper.toDto(pet)))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        pet.setName(petDto.getName());
        pet.setSpecies(petDto.getSpecies());
        pet.setBreed(petDto.getBreed());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setWeight(petDto.getWeight());
        pet.setFoodPreferences(petDto.getFoodPreferences());

        petRepository.save(pet);
        return pet;
    }

    @Override
    public void deletePet(Long id) {
        Optional<User> user = AppUtil.getCurrentUser(userRepository);
        if (user.isEmpty() || !isPetOwnedByUser(id))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        // TODO: no deber permitir eliminar si siene reservas activas

        petRepository.findById(id).ifPresent(p -> {
            p.setState(false);
            petRepository.save(p);
        });
    }

    private boolean isPetOwnedByUser(PetDto pet) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> petRepository.existsByIdAndOwnerDocument(pet.getId(), user.getDocument())
                ).orElse(false);
    }

    private boolean isPetOwnedByUser(Long id) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> petRepository.existsByIdAndOwnerDocument(id, user.getDocument())
                ).orElse(false);
    }
}
