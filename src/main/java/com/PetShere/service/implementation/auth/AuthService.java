package com.PetShere.service.implementation.auth;

import com.PetShere.persistence.model.user.Role;
import com.PetShere.persistence.model.user.User;
import com.PetShere.presentation.dto.auth.AuthResponse;
import com.PetShere.presentation.dto.auth.LoginRequest;
import com.PetShere.presentation.dto.auth.RegisterRequest;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.util.Validations;
import com.PetShere.util.Constants;
import com.PetShere.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
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

        // TODO: enviar mail de verificación antes de guardar
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
