package com.PetShere.persistence.model.reservation;

import com.PetShere.persistence.model.facture.Facture;
import com.PetShere.persistence.model.pet.Pet;
import com.PetShere.persistence.model.service.Service;
import com.PetShere.persistence.model.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "facture_id", nullable = false)
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    private Long price;
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
