package app.PetShere.services;

import app.PetShere.models.Role;
import app.PetShere.models.User;
import app.PetShere.security.AuthResponse;
import app.PetShere.security.LoginRequest;
import app.PetShere.security.RegisterRequest;
import app.PetShere.repositories.UserRepository;
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

    // TODO: cambiar a flujos de validación
    /*
        @Autowired
        importar security.ValidationService validationService;

    try {
        validationService.validateUser(); (lanza la excepción personalizada)
        ...build()
        LOGGER...
        return ...
    } catch (InvalidUserException e) {
        LOGGER...
    }
     */

    public AuthResponse register(RegisterRequest registerRequest) {
        // TODO: do validation before build
        User user = User.builder()
                .document(registerRequest.getDocument())
                .username(registerRequest.getUsername())
                .firstName(registerRequest.getFirstName())
                .middleName(registerRequest.getMiddleName())
                .lastName(registerRequest.getLastName())
                .secondSurname(registerRequest.getSecondSurname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .state(true)
                .role(Role.CLIENT)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // TODO: do validation before build
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        return AuthResponse.builder()
                .token(jwtService.getToken(userDetails))
                .build();
    }
}
