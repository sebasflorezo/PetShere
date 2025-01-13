package app.PetShere.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String document;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondSurname;
    private String password;
    private String email;
}
