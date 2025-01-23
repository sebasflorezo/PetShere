package app.PetShere.services.admin;

import app.PetShere.models.user.Role;
import app.PetShere.models.user.User;

import java.util.List;

public interface AdminService {
    List<User> getUsers();

    void changeUserRole(Long id, String role);
}
