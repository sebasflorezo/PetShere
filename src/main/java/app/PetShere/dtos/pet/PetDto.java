package app.PetShere.dtos.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
    private Long id;
    private String ownerDocument;
    private String name;
    private String species;
    private String breed;
    private Date birthDate;
    private Double weight;
    private String foodPreferences;
    private Boolean state;
}
// TODO: agregar la foto
