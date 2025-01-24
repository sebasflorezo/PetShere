package app.PetShere.mappers.user;

import app.PetShere.dtos.user.UserDto;
import app.PetShere.models.user.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .document(user.getDocument())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .secondSurname(user.getSecondSurname())
                .phone(user.getPhone())
                .direction(user.getDirection())
                .state(user.getState())
                .role(user.getRole())
                .build();
    }
}
