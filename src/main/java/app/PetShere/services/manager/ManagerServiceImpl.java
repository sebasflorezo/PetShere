package app.PetShere.services.manager;

import app.PetShere.dtos.user.UserRolesDto;
import app.PetShere.mappers.user.UserMapper;
import app.PetShere.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository userRepository;

    @Override
    public List<UserRolesDto> getUserRoles() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserRolesDto)
                .collect(Collectors.toList());
    }
}
