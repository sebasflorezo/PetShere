package com.PetShere.service.interfaces;

import com.PetShere.presentation.dto.user.UserRolesDto;

import java.util.List;

public interface IManagerService {
    List<UserRolesDto> getUserRoles();
}
