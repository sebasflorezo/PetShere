package com.PetShere.util;

public class Constants {
    // Configurations
    public static final String BEARER_START = "Bearer ";
    public static final String JWT_AUDIENCE = "https://api.petshere.com";
    public static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 10;
    public static final long TOKEN_REFRESH_TIME = 1000 * 60 * 60 * 24 * 7;

    // Authorization
    public static final String ADMIN_AUTHORITY = "hasAuthority('ADMIN')";
    public static final String CLIENT_AUTHORITY = "hasAuthority('CLIENT')";
    public static final String MANAGER_AUTHORITY = "hasAuthority('MANAGER')";
    public static final String CARER_AUTHORITY = "hasAuthority('CARER')";

    // Log messages
    public static final String JWT_EXPIRED_MSG = "JWT está expirado";
    public static final String JWT_INVALID_AUDIENCE = "JWT con audiencia inválida";
    public static final String JWT_NOT_FOUND_MSG = "JWT no encontrado";
    public static final String JWT_EMAIL_NOT_FOUND_MSG = "JWT email no encontrado";
    public static final String JWT_PROCESSING_ERROR = "Error procesando JWT - ";

    public static final String CHANGE_USER_ROLE_ERROR = "Error al cambiar el rol del usuario - ";

    // Request error messages
    public static final String BAD_REQUEST_MSG = "Solicitud incorrecta";
    public static final String UNAUTHORIZED_USER = "Usuario no autorizado";

    // General
    public static final String NOT_FOUND_GENERIC = "Recurso no encontrado";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USER_NOT_FOUND_BY_EMAIL = "Usuario no encontrado con email: ";
    public static final String USER_NOT_FOUND_BY_ID = "Usuario no encontrado con id: ";
    public static final String USER_NOT_FOUND_BY_DOCUMENT = "Usuario no encontrado con documento: ";
    public static final String PET_NOT_FOUND_BY_ID = "Mascota no encontrada con id: ";
    public static final String JWT_CANT_RENEW = "El token no puede ser renovado";
    public static final String SERVICE_NOT_FOUND = "Servicio no encontrado";

    // Validations
    public static final String NULL_REGISTER_MESSAGE = "Formulario de registro no es válido";
    public static final String NULL_LOGIN_MESSAGE = "Formulario de inicio de sesión no es válido";
    public static final String EMPTY_DOCUMENT_MESSAGE = "El documento no puede estar vacío";
    public static final String EMPTY_FIRST_NAME_MESSAGE = "El primer nombre no puede estar vacío";
    public static final String EMPTY_LASTNAME_MESSAGE = "El primer apellido no puede estar vacío";
    public static final String INVALID_EMAIL_MESSAGE = "Correo electrónico no es válido";
    public static final String INVALID_DOCUMENT_MESSAGE = "El documento ingresado no es válido";
    public static final String INVALID_PASSWORD_MESSAGE = """
            La contraseña debe:
            - Tener al menos 9 caracteres
            - Incluir mayúsculas y minúsculas
            - Caracteres numéricos
            - Caracteres especiales
            """;

    public static final String EMPTY_DATE_MESSAGE = "La fecha no puede ser nula";
    public static final String INVALID_PRICE_MESSAGE = "El precio no es válido";
    public static final String EMPTY_SERVICE_NAME_MESSAGE = "El nombre del servicio no puede estar vacío";
    public static final String PAYMENT_METHOD_NOT_FOUND = "Método de pago no válido";
    public static final String ROLE_NOT_FOUND_MESSAGE = "Rol no encontrado";
    public static final String END_DATE_BEFORE_START_DATE_MESSAGE = "La fecha final no puede ser anterior a la fecha inicial";

    // Regular expresions
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    // Email subjects
    public static final String EMAIL_WELCOME_MESSAGE = "Bienvenido a PetShere, ";
}
