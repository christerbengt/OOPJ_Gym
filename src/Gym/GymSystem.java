package Gym;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GymSystem {
    private List<Customer> customers = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();
    private DateHandler dateHandler = new DateHandler();

    public List<Customer> readCustomerFile(String fileName) throws Exception {
        List<String> rader = fileHandler.readFromFile(fileName);
        for (String rad : rader) {
            String[] parts = rad.split(", ");
            if (parts.length == 3) {
                String personalNumber = parts[0];
                String name = parts[1];
                LocalDate latestPayment = LocalDate.parse(parts[2]);
                customers.add(new Customer(personalNumber, name, latestPayment));
            }
        }
        return customers;
    }

    public Customer findCustomer(String searchterm) {
        for (Customer customer : customers) {
            if (customer.getPersonalNumber().equals(searchterm) || customer.getName().equals(searchterm)) {
                return customer;
            }
        }
        return null;
    }

    public String categorizeCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }
        if (dateHandler.withinYear(customer.getLatestPayment())) {
            return "Current customer";
        }else {
            return "Not currently a customer";
        }
    }
}