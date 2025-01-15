package app.PetShere.security;

import app.PetShere.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(Constants.PASSWORD_REGEX);

    public void validateRegisterRequest(RegisterRequest registerRequest) {
        if (registerRequest == null)
            throw new IllegalArgumentException(Constants.NULL_REGISTER_MESSAGE);

        validateEmail(registerRequest.getEmail());
        validatePasswordRegister(registerRequest.getPassword());

        if (!StringUtils.hasText(registerRequest.getDocument()))
            throw new IllegalArgumentException(Constants.EMPTY_DOCUMENT_MESSAGE);

        if (!StringUtils.hasText(registerRequest.getFirstName()))
            throw new IllegalArgumentException(Constants.EMPTY_FIRST_NAME_MESSAGE);

        if (!StringUtils.hasText(registerRequest.getLastName()))
            throw new IllegalArgumentException(Constants.EMPTY_LASTNAME_MESSAGE);
    }

    public void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null)
            throw new IllegalArgumentException(Constants.NULL_LOGIN_MESSAGE);

        validateEmail(loginRequest.getEmail());
        validatePasswordLogin(loginRequest.getPassword());
    }

    public void validateEmail(String email) {
        if (!StringUtils.hasText(email) || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException(Constants.INVALID_EMAIL_MESSAGE);
    }

    private void validatePasswordLogin(String password) {
        if (!StringUtils.hasText(password))
            throw new IllegalArgumentException(Constants.INVALID_PASSWORD_MESSAGE);
    }

    public void validatePasswordRegister(String password) {
        if (!StringUtils.hasText(password) || !PASSWORD_PATTERN.matcher(password).matches())
            throw new IllegalArgumentException(Constants.INVALID_PASSWORD_MESSAGE);
    }
}
