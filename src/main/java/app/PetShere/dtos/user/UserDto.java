package app.PetShere.dtos.user;

import app.PetShere.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String document;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondSurname;
    private String phone;
    private String direction;
    private Boolean state;
    private Role role;
}
