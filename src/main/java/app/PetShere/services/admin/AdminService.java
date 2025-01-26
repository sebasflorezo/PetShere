package app.PetShere.services.admin;

import app.PetShere.dtos.user.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getUsers();

    void changeUserRole(Long id, String role);
}
