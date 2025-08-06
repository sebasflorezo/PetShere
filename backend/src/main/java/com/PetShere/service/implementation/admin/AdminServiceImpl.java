package com.PetShere.service.implementation.admin;

import com.PetShere.presentation.dto.user.UserDto;
import com.PetShere.persistence.model.user.Role;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.service.interfaces.IAdminService;
import com.PetShere.util.Validations;
import com.PetShere.util.Constants;
import com.PetShere.util.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final IUserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void changeUserRole(Long id, String role) {
        Validations.validateRole(role);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error(Constants.USER_NOT_FOUND_BY_ID);
            return new IllegalArgumentException(Constants.USER_NOT_FOUND_BY_ID);
        });
        user.setRole(Role.valueOf(role));

        userRepository.save(user);
    }
}
