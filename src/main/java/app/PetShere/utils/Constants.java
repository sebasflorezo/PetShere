package app.PetShere.utils;

import java.util.Date;

public class Constants {
    // Validations
    public static final String NULL_REGISTER_MESSAGE = "RegisterRequest no puede ser null";
    public static final String NULL_LOGIN_MESSAGE = "LoginRequest no puede ser null";
    public static final String EMPTY_DOCUMENT_MESSAGE = "El documento no puede estar vacío";
    public static final String EMPTY_FIRST_NAME_MESSAGE = "El primer nombre no puede estar vacío";
    public static final String EMPTY_LASTNAME_MESSAGE = "El primer apellido no puede estar vacío";
    public static final String INVALID_EMAIL_MESSAGE = "Correo electrónico no es válido";
    public static final String INVALID_PASSWORD_MESSAGE = "La contraseña debe tener al menos 9 caracteres";
    public static final String EMAIL_REGEX = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    
    // Utility methods
    public static Date tenMinutesFromNow() {
        // 600_000 = 1000 * 60 * 10
        return new Date(System.currentTimeMillis() + 600_000);
    }
}
