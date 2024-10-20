package Gym;
import java.time.LocalDate;
import java.util.Objects;

// Represents a customer in the gym system. This class is immutable to ensure data integrity.

public class Customer {
    private final String personalNumber;
    private final String name;
    private final LocalDate latestPayment;

    // Constructs a new Customer with the given details.
    public Customer(String personalNumber, String name, LocalDate latestPayment) {
        this.personalNumber = personalNumber;
        this.name = name;
        this.latestPayment = latestPayment;
    }

    // Getters (no setters to ensure immutability)
    public String getPersonalNumber() { return personalNumber; }
    public String getName() { return name; }
    public LocalDate getLatestPayment() { return latestPayment; }

    // Compares this customer to another object for equality. Customers are considered equal if they have the same personal number.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(personalNumber, customer.personalNumber);
    }
    // Generates a hash code for this customer based on the personal number to match the equals method.
    @Override
    public int hashCode() {
        return Objects.hash(personalNumber);
    }
    // Returns a string representation of this customer.
    @Override
    public String toString() {
        return "Customer{" +
                "personalNumber='" + personalNumber + '\'' +
                ", name='" + name + '\'' +
                ", latestPayment=" + latestPayment +
                '}';
    }
}