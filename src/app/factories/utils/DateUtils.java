package app.factories.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    // Method to parse a date with the default format
    public static LocalDate parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMAT_PATTERN);
    }

    // Method to parse a date with a specific format
    public static LocalDate parseDate(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }

    // Method to calculate the duration between two dates
    public static long calculateDuration(LocalDate startDate, LocalDate endDate) {
        return Math.abs(startDate.until(endDate).getDays());
    }

    // Method to get the current date
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}