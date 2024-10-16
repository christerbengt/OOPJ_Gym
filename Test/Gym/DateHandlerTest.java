package Gym;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateHandlerTest {

    @Test
    public void testCalculateTimeDiff() {
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 7, 1);

        DateHandler dateHandler = new DateHandler();
        long months = dateHandler.calculateTimeDiff(date1, date2);

        assertEquals(6, months);
    }

    @Test
    public void testWithinYear() {
        LocalDate thisDay = LocalDate.now();
        LocalDate withinYear = thisDay.minusMonths(11);
        LocalDate outsideYear = thisDay.minusMonths(13);

        DateHandler dateHandler = new DateHandler();

        assertTrue(dateHandler.withinYear(withinYear));
        assertFalse(dateHandler.withinYear(outsideYear));
    }
}