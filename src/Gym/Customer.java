package Gym;
import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private final String personalNumber;
    private final String name;
    private final LocalDate latestPayment;

    public Customer(String personalNumber, String name, LocalDate latestPayment) {
        this.personalNumber = personalNumber;
        this.name = name;
        this.latestPayment = latestPayment;
    }

    // Getters (no setters to ensure immutability)
    public String getPersonalNumber() { return personalNumber; }
    public String getName() { return name; }
    public LocalDate getLatestPayment() { return latestPayment; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(personalNumber, customer.personalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "personalNumber='" + personalNumber + '\'' +
                ", name='" + name + '\'' +
                ", latestPayment=" + latestPayment +
                '}';
    }
}