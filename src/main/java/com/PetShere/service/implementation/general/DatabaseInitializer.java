package com.PetShere.service.implementation.general;

import com.PetShere.persistence.model.user.Role;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.user.IUserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer {
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
    }
}
