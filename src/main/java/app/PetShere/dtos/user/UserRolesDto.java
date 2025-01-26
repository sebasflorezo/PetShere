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
public class UserRolesDto {
    private Long id;
    private String email;
    private String name;
    private Boolean state;
    private Role role;
}
