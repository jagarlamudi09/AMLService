package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private Util() {

    }

    public static boolean validateTime(final String time) {
        String TIME24HOURS_PATTERN =
                "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}
