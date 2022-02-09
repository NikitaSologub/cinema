package by.itacademy.final_task_nikita_sologub.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateFormatter {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private DateFormatter() {
    }

    public static String getFormattedDate(LocalDateTime unformattedDate) {
        return unformattedDate.format(formatter);
    }
}