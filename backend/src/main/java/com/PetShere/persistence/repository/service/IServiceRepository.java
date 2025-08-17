package com.PetShere.persistence.repository.service;

import com.PetShere.persistence.model.service.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Service, Long> {
}
