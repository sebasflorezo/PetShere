package com.PetShere.persistence.repository.user;

import com.PetShere.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByDocument(String document);
}
