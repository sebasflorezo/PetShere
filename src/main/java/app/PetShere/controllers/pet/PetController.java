package app.PetShere.controllers.pet;

import app.PetShere.dtos.pet.PetDto;
import app.PetShere.services.pet.PetServiceImpl;
import app.PetShere.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetServiceImpl petServiceImpl;

    @GetMapping
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> getPets() {
        return ResponseEntity.ok(petServiceImpl.getAllPets());
    }

    @GetMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getPet(@PathVariable Long id) {
        return ResponseEntity.ok(petServiceImpl.getPetById(id));
    }

    @GetMapping("/{document}/pets")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> getOwnerPets(@PathVariable String document) {
        try {
            List<PetDto> pets = petServiceImpl.getPetsByOwnerDocument(document);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }

    // + " or " + Constants.ADMIN_AUTHORITY -> (para dar autoridad a más tipos de usuarios)
    @PostMapping("/{document}/pets")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> createPet(@PathVariable String document, @RequestBody PetDto petDto) {
        try {
            petDto.setOwnerDocument(document);
            petServiceImpl.createPet(petDto);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(petDto.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
        try {
            petServiceImpl.updatePet(id, petDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(Constants.CLIENT_AUTHORITY)
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        try {
            petServiceImpl.deletePet(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }

}
