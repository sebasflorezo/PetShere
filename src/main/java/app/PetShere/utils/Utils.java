package app.PetShere.utils;

import java.util.Date;

public class Utils {

    public static Date tenMinutesFromNow() {
        return new Date(System.currentTimeMillis() + minutesToMiliseconds(10));
    }

    public static int minutesToMiliseconds(int minutes) {
        return 1000 * 60 * minutes;
    }

}
