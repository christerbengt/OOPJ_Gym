package Gym;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests to verify the correct creation and functionality of Customer objects.
public class CustomerTest {

    // Tests creation of a Customer object and ensures all fields are correctly set.
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