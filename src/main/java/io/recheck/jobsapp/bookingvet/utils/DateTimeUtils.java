package io.recheck.jobsapp.bookingvet.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static String datePattern = "yyyy-MM-dd HH:mm";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

    public static LocalDateTime convertOrReturnNull(String s) {
        if (StringUtils.hasText(s)) {
            return LocalDateTime.parse(s, dateTimeFormatter);
        }
        return null;
    }

    public static String convertOrReturnNull(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return localDateTime.format(dateTimeFormatter);
        }
        return null;
    }

    public static void validateIsBeforeOrIsEqual(LocalDateTime localDateTime) throws Exception {
        if (localDateTime != null) {
            if (localDateTime.isBefore(LocalDateTime.now()) || localDateTime.isEqual(LocalDateTime.now())) {
                throw new Exception("Selected date is in the past!");
            }
        }
    }

}
