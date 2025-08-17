package com.PetShere.service.interfaces;

import com.PetShere.presentation.dto.user.UserDto;

import java.util.List;

public interface IAdminService {
    List<UserDto> getUsers();

    void changeUserRole(Long id, String role);
}
