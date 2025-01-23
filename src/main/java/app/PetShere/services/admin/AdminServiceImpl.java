package app.PetShere.services.admin;

import app.PetShere.models.user.Role;
import app.PetShere.models.user.User;
import app.PetShere.repositories.user.UserRepository;
import app.PetShere.utils.Validations;
import app.PetShere.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
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
