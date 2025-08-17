package com.PetShere.persistence.model.pet;

import com.PetShere.persistence.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
// TODO: agregar la foto
