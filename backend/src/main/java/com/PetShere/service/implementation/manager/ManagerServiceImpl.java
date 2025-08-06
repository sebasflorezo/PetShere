package com.PetShere.service.implementation.manager;

import com.PetShere.presentation.dto.user.UserRolesDto;
import com.PetShere.service.interfaces.IManagerService;
import com.PetShere.util.mapper.user.UserMapper;
import com.PetShere.persistence.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements IManagerService {

    private final IUserRepository userRepository;

    @Override
    public List<UserRolesDto> getUserRoles() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserRolesDto)
                .collect(Collectors.toList());
    }

    // TODO: implementar métodos para generar reportes, sea acá o en reports
}
