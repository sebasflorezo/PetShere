package com.PetShere.util;

import com.PetShere.persistence.model.facture.PaymentMethod;
import com.PetShere.presentation.dto.auth.LoginRequest;
import com.PetShere.presentation.dto.auth.RegisterRequest;
import com.PetShere.presentation.dto.pet.PetDto;
import com.PetShere.persistence.model.user.Role;
import com.PetShere.presentation.dto.reservation.ReservationDto;
import com.PetShere.presentation.dto.service.ServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Slf4j
public class Validations {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(Constants.PASSWORD_REGEX);

    public static void validateRegisterRequest(RegisterRequest registerRequest) {
        if (registerRequest == null)
            throw new IllegalArgumentException(Constants.NULL_REGISTER_MESSAGE);

        validateEmail(registerRequest.getEmail());
        validatePasswordRegister(registerRequest.getPassword());
        validateDocument(registerRequest.getDocument());

        if (!StringUtils.hasText(registerRequest.getFirstName()))
            throw new IllegalArgumentException(Constants.EMPTY_FIRST_NAME_MESSAGE);

        if (!StringUtils.hasText(registerRequest.getLastName()))
            throw new IllegalArgumentException(Constants.EMPTY_LASTNAME_MESSAGE);
    }

    public static void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null)
            throw new IllegalArgumentException(Constants.NULL_LOGIN_MESSAGE);

        validateEmail(loginRequest.getEmail());
        validatePasswordLogin(loginRequest.getPassword());
    }

    private static void validateDocument(String document) {
        if (document == null || document.isEmpty() || document.length() >= 12 || !document.matches("\\d+"))
            throw new IllegalArgumentException(Constants.INVALID_DOCUMENT_MESSAGE);
    }

    public static void validateEmail(String email) {
        if (!StringUtils.hasText(email) || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException(Constants.INVALID_EMAIL_MESSAGE);
    }

    private static void validatePasswordLogin(String password) {
        if (!StringUtils.hasText(password))
            throw new IllegalArgumentException(Constants.INVALID_PASSWORD_MESSAGE);
    }

    public static void validatePasswordRegister(String password) {
        if (!StringUtils.hasText(password) || !PASSWORD_PATTERN.matcher(password).matches())
            throw new IllegalArgumentException(Constants.INVALID_PASSWORD_MESSAGE);
    }

    public static void validatePetDto(PetDto petDto) {
        validateDocument(petDto.getOwnerDocument());
        // TODO: agregar más validaciones
    }

    public static void validateFacture(String document, String paymentMethod) {
        validateDocument(document);
        validatePaymentMethod(paymentMethod);

        if (paymentMethod == null)
            throw new IllegalArgumentException(Constants.PAYMENT_METHOD_NOT_FOUND);
    }

    private static void validatePaymentMethod(String paymentMethod) {
        try {
            PaymentMethod.valueOf(paymentMethod);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(Constants.PAYMENT_METHOD_NOT_FOUND);
        }
    }

    public static void validateRole(String role) {
        try {
            Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(Constants.ROLE_NOT_FOUND_MESSAGE);
        }
    }

    public static void validateService(ServiceDto serviceDto) {
        if (serviceDto.getName() == null || serviceDto.getName().isEmpty())
            throw new IllegalArgumentException(Constants.EMPTY_SERVICE_NAME_MESSAGE);

        if (serviceDto.getPrice() == null || serviceDto.getPrice() <= 0)
            throw new IllegalArgumentException(Constants.INVALID_SERVICE_PRICE_MESSAGE);
    }

    public static void validateReservation(ReservationDto reservationDto) {
        // TODO
    }
}
