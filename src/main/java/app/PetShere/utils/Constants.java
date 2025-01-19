package app.PetShere.utils;

public class Constants {
    // Configurations
    public static final String BEARER_START = "Bearer ";
    public static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 10; //60 * 24;
    public static final long TOKEN_REFRESH_TIME = 1000 * 60 * 60 * 24 * 7;

    // Log messages
    public static final String JWT_NOT_FOUND_MSG = "JWT not found";
    public static final String JWT_EMAIL_NOT_FOUND_MSG = "JWT doesn't contains a email";
    public static final String JWT_PROCESSING_ERROR = "Error processing JWT - ";

    // General
    public static final String USER_NOT_FOUND_BY_EMAIL = "Usuario no encontrado con email: ";
    public static final String JWT_CANT_RENEW = "El token no puede ser renovado";

    // Validations
    public static final String NULL_REGISTER_MESSAGE = "Formulario de registro no es válido";
    public static final String NULL_LOGIN_MESSAGE = "Formulario de inicio de sesión no es válido";
    public static final String EMPTY_DOCUMENT_MESSAGE = "El documento no puede estar vacío";
    public static final String EMPTY_FIRST_NAME_MESSAGE = "El primer nombre no puede estar vacío";
    public static final String EMPTY_LASTNAME_MESSAGE = "El primer apellido no puede estar vacío";
    public static final String INVALID_EMAIL_MESSAGE = "Correo electrónico no es válido";
    public static final String INVALID_PASSWORD_MESSAGE = """
            La contraseña debe:
            - Tener al menos 9 caracteres
            - Incluir mayúsculas y minúsculas
            - Caracteres numéricos
            - Caracteres especiales
            """;

    // Regular expresions
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
}
