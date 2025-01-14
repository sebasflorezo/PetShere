package app.PetShere.services;

import app.PetShere.models.Role;
import app.PetShere.models.User;
import app.PetShere.security.AuthResponse;
import app.PetShere.security.LoginRequest;
import app.PetShere.security.RegisterRequest;
import app.PetShere.repositories.UserRepository;
import app.PetShere.security.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ValidationService validationService;

    public AuthResponse register(RegisterRequest registerRequest) {
        validationService.validateRegisterRequest(registerRequest);

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

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        validationService.validateLoginRequest(loginRequest);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuario no encontrado con email: " + loginRequest.getEmail())
                );

        return AuthResponse.builder()
                .token(jwtService.getToken(userDetails))
                .build();
    }
}
