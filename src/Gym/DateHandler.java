package Gym;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Class for handling date-related operations in the gym system.
public class DateHandler {
    // Calculates time difference (in months) between two dates.
    public long calculateTimeDiff(LocalDate date1, LocalDate date2) {
        return ChronoUnit.MONTHS.between(date1, date2);
    }
    // Checks if a given date is within one year from current date.
    public boolean withinYear(LocalDate date) {
        return date.isAfter(LocalDate.now().minusYears(1));
    }
}
