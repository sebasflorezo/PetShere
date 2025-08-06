package com.PetShere.service.implementation.general;

import com.PetShere.persistence.model.user.Role;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.model.service.Service;
import com.PetShere.persistence.repository.service.IServiceRepository;
import com.PetShere.persistence.repository.user.IUserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final IServiceRepository serviceRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        if (userRepository.count() == 0)
            userRepository.save(User.builder()
                    .document("0")
                    .email("admin@admin.com")
                    .direction("admin")
                    .state(true)
                    .password(passwordEncoder.encode("Admin123456*"))
                    .phone("admin")
                    .role(Role.ADMIN)
                    .firstName("admin")
                    .middleName("admin")
                    .lastName("admin")
                    .secondSurname("admin")
                    .build());

        if (serviceRepository.count() == 0)
            serviceRepository.saveAll(initialServices());
    }

    private List<Service> initialServices() {
        return List.of(
                Service
                        .builder()
                        .name("Baño")
                        .price(50000L)
                        .state(true)
                        .build(),
                Service
                        .builder()
                        .name("Vacunación")
                        .price(65000L)
                        .state(true)
                        .build(),
                Service
                        .builder()
                        .name("Transporte")
                        .price(22000L)
                        .state(true)
                        .build(),
                Service
                        .builder()
                        .name("Guardería")
                        .price(28000L)
                        .state(true)
                        .build(),
                Service
                        .builder()
                        .name("Colegio")
                        .price(20000L)
                        .state(true)
                        .build(),
                Service
                        .builder()
                        .name("Hotel")
                        .price(51000L)
                        .state(true)
                        .build()
        );
    }
}
