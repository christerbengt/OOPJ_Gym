package Gym;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateHandler {
    public long calculateTimeDiff(LocalDate date1, LocalDate date2) {
        return ChronoUnit.MONTHS.between(date1, date2);
    }

    public boolean withinYear(LocalDate date) {
        return date.isAfter(LocalDate.now().minusYears(1));
    }
}
