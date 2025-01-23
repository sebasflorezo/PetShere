package app.PetShere.services.auth;

import app.PetShere.models.user.Role;
import app.PetShere.models.user.User;
import app.PetShere.models.auth.AuthResponse;
import app.PetShere.models.auth.LoginRequest;
import app.PetShere.models.auth.RegisterRequest;
import app.PetShere.repositories.UserRepository;
import app.PetShere.utils.Validations;
import app.PetShere.utils.Constants;
import app.PetShere.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {
        Validations.validateRegisterRequest(registerRequest);

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .document(registerRequest.getDocument())
                .firstName(registerRequest.getFirstName())
                .middleName(registerRequest.getMiddleName())
                .lastName(registerRequest.getLastName())
                .secondSurname(registerRequest.getSecondSurname())
                .phone(registerRequest.getPhone())
                .direction(registerRequest.getDirection())
                .state(true)
                .role(Role.CLIENT)
                .build();

        // TODO: send verification email before save
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Validations.validateLoginRequest(loginRequest);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new NotFoundException(Constants.USER_NOT_FOUND_BY_EMAIL + loginRequest.getEmail())
                );

        return AuthResponse.builder()
                .token(jwtService.getToken(userDetails))
                .build();
    }
}
