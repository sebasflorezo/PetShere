package app.PetShere.services.manager;

import app.PetShere.dtos.user.UserRolesDto;

import java.util.List;

public interface ManagerService {
    List<UserRolesDto> getUserRoles();
}
