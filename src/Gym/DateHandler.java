package Gym;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateHandler {
    public long calculateTimeDiff(LocalDate date1, LocalDate date2) {
        return ChronoUnit.MONTHS.between(date1, date2);
    }

    public boolean withinYear(LocalDate date) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return date.isAfter(oneYearAgo);
    }
}
