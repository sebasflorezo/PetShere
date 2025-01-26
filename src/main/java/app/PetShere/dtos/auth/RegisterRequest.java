package app.PetShere.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String document;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondSurname;
    private String phone;
    private String direction;
}
