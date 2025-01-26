package app.PetShere.models.pet;

import app.PetShere.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PETS")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_document", nullable = false)
    private User owner;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String species;
    private String breed;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(nullable = false)
    private Double weight;
    private String foodPreferences;
    private Boolean state;
}
// TODO: agregar la foto
