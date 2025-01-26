package app.PetShere.services.pet;

import app.PetShere.dtos.pet.PetDto;
import app.PetShere.exceptions.AuthorizationDeniedException;
import app.PetShere.exceptions.ResourceNotFoundException;
import app.PetShere.mappers.pet.PetMapper;
import app.PetShere.models.pet.Pet;
import app.PetShere.models.user.User;
import app.PetShere.repositories.pet.PetRespository;
import app.PetShere.repositories.user.UserRepository;
import app.PetShere.utils.Constants;
import app.PetShere.utils.Utils;
import app.PetShere.utils.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRespository petRespository;
    private final UserRepository userRepository;

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
        Optional<User> user = Utils.getCurrentUser(userRepository);
        if (user.isEmpty() || !user.get().getDocument().equals(document))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        return petRespository.findByOwnerDocument(document)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PetDto> getPetsByOwnerDocument() {
        return Utils.getCurrentUser(userRepository)
                .map(User::getDocument)
                .map(petRespository::findByOwnerDocument)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPet(PetDto petDto) {
        User user = Utils.getCurrentUser(userRepository)
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
        Optional<User> user = Utils.getCurrentUser(userRepository);
        if (user.isEmpty() || !isPetOwnedByUser(id))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        petRespository.findById(id).ifPresent(p -> {
            p.setState(false);
            petRespository.save(p);
        });
    }

    private boolean isPetOwnedByUser(PetDto pet) {
        return Utils.getCurrentUser(userRepository)
                .map(user -> petRespository.existsByIdAndOwnerDocument(pet.getId(), user.getDocument())
                ).orElse(false);
    }

    private boolean isPetOwnedByUser(Long id) {
        return Utils.getCurrentUser(userRepository)
                .map(user -> petRespository.existsByIdAndOwnerDocument(id, user.getDocument())
                ).orElse(false);
    }
}
