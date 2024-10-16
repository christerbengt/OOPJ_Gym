package Gym;
import java.time.LocalDate;

public class Customer {
    private String personalNumber;
    private String name;
    private LocalDate latestPayment;

    public Customer(String personalNumber, String name, LocalDate latestPayment) {
        this.personalNumber = personalNumber;
        this.name = name;
        this.latestPayment = latestPayment;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLatestPayment() {
        return latestPayment;
    }
}