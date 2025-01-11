package app.PetShere.services;

import app.PetShere.models.Rol;
import app.PetShere.security.AuthResponse;
import app.PetShere.security.LoginRequest;
import app.PetShere.security.RegisterRequest;
import app.PetShere.models.Usuario;
import app.PetShere.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest registerRequest) {
        Usuario usuario = Usuario.builder()
                .documento(registerRequest.getDocumento())
                .username(registerRequest.getUsername())
                .primerNombre(registerRequest.getPrimerNombre())
                .segundoNombre(registerRequest.getSegundoNombre())
                .primerApellido(registerRequest.getPrimerApellido())
                .segundoApellido(registerRequest.getSegundoApellido())
                .password(registerRequest.getPassword())
                .correo(registerRequest.getCorreo())
                .estado(true)
                .rol(Rol.CLIENTE)
                .build();

        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        return null;
    }
}
