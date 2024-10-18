package Gym;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GymSystem {
    protected List<Customer> customers = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();
    private DateHandler dateHandler = new DateHandler();

    private String customerFile = "src/register.txt";
    private String trainingDataFile = "src/training_data.txt";

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
        System.out.println("Number of lines read from file: " + lines.size());

        customers.clear();

        for (int i = 0; i < lines.size(); i += 2) {
            if (i + 1 < lines.size()) {
                String customerLine = lines.get(i);
                String dateLine = lines.get(i + 1);
                System.out.println("Processing customer: " + customerLine);
                System.out.println("Processing date: " + dateLine);

                String[] parts = customerLine.split(", ");
                if (isValidCustomerData(parts, dateLine)) {
                    String personalNumber = parts[0].trim();
                    String name = parts[1].trim();
                    LocalDate latestPayment = LocalDate.parse(dateLine.trim());
                    customers.add(new Customer(personalNumber, name, latestPayment));
                } else {
                    System.out.println("Invalid customer data: " + customerLine + " " + dateLine);
                }
            }
        }
        return customers;
    }

    private boolean isValidCustomerData(String[] parts, String dateLine) {
        return parts.length == 2 &&
                parts[0].trim().matches("\\d{10}") &&
                !parts[1].trim().isEmpty() &&
                dateLine.trim().matches("\\d{4}-\\d{2}-\\d{2}");
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
        return dateHandler.withinYear(customer.getLatestPayment()) ? "Nuvarande medlem" : "Ej nuvarande medlem";
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