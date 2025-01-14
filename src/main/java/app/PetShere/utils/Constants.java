package app.PetShere.utils;

import java.util.Date;

public class Constants {

    public static Date tenMinutesFromNow() {
        // 600_000 = 1000 * 60 * 10
        return new Date(System.currentTimeMillis() + 600_000);
    }

}
