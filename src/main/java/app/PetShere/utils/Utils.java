package app.PetShere.utils;

import app.PetShere.models.user.User;
import app.PetShere.repositories.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class Utils {

    public static Optional<User> getCurrentUser(UserRepository userRepository) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(
                authentication.getPrincipal().toString()
        );
    }

    public static Date tenMinutesFromNow() {
        return new Date(System.currentTimeMillis() + minutesToMiliseconds(10));
    }

    public static int minutesToMiliseconds(int minutes) {
        return 1000 * 60 * minutes;
    }

}
