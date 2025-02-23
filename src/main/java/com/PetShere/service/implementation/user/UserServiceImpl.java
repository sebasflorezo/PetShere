package com.PetShere.service.implementation.user;

import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.presentation.dto.user.UserDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.interfaces.IUserService;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Constants;
import com.PetShere.util.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserDto getCurrentUser() {
        return AppUtil.getCurrentUser(userRepository)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));
    }
}
