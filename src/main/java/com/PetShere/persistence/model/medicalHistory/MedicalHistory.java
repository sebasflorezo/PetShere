package com.PetShere.persistence.model.medicalHistory;

import com.PetShere.persistence.model.pet.Pet;
import com.PetShere.persistence.model.user.User;
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
@Table(name = "MEDICAL_HISTORY")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date consultationDate;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String treatment;
    @ManyToOne
    @JoinColumn(name = "carer_id", nullable = false)
    private User carer;
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}
