package Gym;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    @Test
    public void testCreateCustomer() {
        String personalNumber = "7502031234";
        String name = "Anna Andersson";
        LocalDate latestPayment = LocalDate.of(2023, 5, 3);

        Customer customer = new Customer(personalNumber, name, latestPayment);

        assertEquals(personalNumber, customer.getPersonalNumber());
        assertEquals(name, customer.getName());
        assertEquals(latestPayment, customer.getLatestPayment());
    }
}