package Gym;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GymSystem {
    protected List<Customer> customers = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();
    private DateHandler dateHandler = new DateHandler();

    private String customerFile = "register.txt";
    private String trainingDataFile = "training_data.txt";

    // Getters and setters for file paths
    public String getCustomerFile() {
        return customerFile;
    }

    public void setCustomerFile(String customerFile) {
        this.customerFile = customerFile;
    }

    public String getTrainingDataFile() {
        return trainingDataFile;
    }

    public void setTrainingDataFile(String trainingDataFile) {
        this.trainingDataFile = trainingDataFile;
    }

    public List<Customer> readCustomerFile() throws Exception {
        List<String> lines = fileHandler.readFromFile(customerFile);
        for (String line : lines) {
            String[] parts = line.split(", ");
            if (FileHandler.isValidCustomerData(parts)) {
                String personalNumber = parts[0].trim();
                String name = parts[1].trim();
                LocalDate latestPayment = LocalDate.parse(parts[2].trim());
                customers.add(new Customer(personalNumber, name, latestPayment));
            } else {
                System.out.println("Invalid customer data: " + line);
            }
        }
        return customers;
    }

    public Customer findCustomer(String searchTerm) {
        return customers.stream()
                .filter(c -> c.getPersonalNumber().equals(searchTerm) || c.getName().equalsIgnoreCase(searchTerm))
                .findFirst()
                .orElse(null);
    }

    public String categorizeCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }
        return dateHandler.withinYear(customer.getLatestPayment()) ? "Nuvarande medlem" : "Ej medlem";
    }

    public void printTrainingData(Customer customer) throws Exception {
        String data = String.format("%s,%s,%s", customer.getName(), customer.getPersonalNumber(), LocalDate.now());
        fileHandler.writeToFile(trainingDataFile, data);
    }

    public static void main(String[] args) {
        GymSystem gymSystem = new GymSystem();
        Scanner scanner = new Scanner(System.in);

        try {
            gymSystem.readCustomerFile();
            System.out.println("Välkommen till Best Gym Evers nya kundregister!");

            while (true) {
                System.out.print("Ange personnummer eller namn (eller 'q' för att avsluta): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q")) {
                    break;
                }

                Customer customer = gymSystem.findCustomer(input);
                if (customer != null) {
                    String category = gymSystem.categorizeCustomer(customer);
                    System.out.println("Kund: " + customer.getName());
                    System.out.println("Personnummer: " + customer.getPersonalNumber());
                    System.out.println("Senaste betalning: " + customer.getLatestPayment());
                    System.out.println("Kategori: " + category);

                    if (category.equals("Nuvarande medlem")) {
                        gymSystem.printTrainingData(customer);
                        System.out.println("Träningsdata registrerad.");
                    } else {
                        System.out.println("OBS: Medlemskapet har gått ut. Vänligen förnya för att träna.");
                    }
                } else {
                    System.out.println("Ingen kund hittad med angivet personnummer eller namn.");
                    System.out.println("Personen har aldrig varit medlem.");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Ett fel uppstod: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}