package Gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class GymSystemTest {
    private GymSystem gymSystem;
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        gymSystem = new GymSystem();
    }

    @Test
    public void testReadCustomerFile() throws Exception {
        GymSystem gymSystem = new GymSystem();

        // Read the file and test the result
        List<Customer> customers = gymSystem.readCustomerFile();

        System.out.println("Number of customers read: " + customers.size());
        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.getName() + ", " + customer.getPersonalNumber());
        }

        assertEquals(14, customers.size(), "Should read 14 customers from the file");

        // Test the first customer
        Customer firstCustomer = customers.get(0);
        assertEquals("7703021234", firstCustomer.getPersonalNumber());
        assertEquals("Alhambra Aromes", firstCustomer.getName());
        assertEquals(LocalDate.parse("2024-07-01"), firstCustomer.getLatestPayment());

        // Test the last customer
        Customer lastCustomer = customers.get(customers.size() - 1);
        assertEquals("7805211234", lastCustomer.getPersonalNumber());
        assertEquals("Nahema Ninsson", lastCustomer.getName());
        assertEquals(LocalDate.parse("2024-08-04"), lastCustomer.getLatestPayment());
    }

    @Test
    public void testFindCustomer() throws Exception {
        gymSystem.customers.add(new Customer("1234567890", "John Doe", LocalDate.of(2023, 1, 1)));
        gymSystem.customers.add(new Customer("8765432109", "Jane Doe", LocalDate.of(2022, 6, 1)));

        // Test finding by personal number
        Customer foundCustomer = gymSystem.findCustomer("1234567890");
        assertNotNull(foundCustomer);
        assertEquals("John Doe", foundCustomer.getName());

        // Test finding by name
        foundCustomer = gymSystem.findCustomer("Jane Doe");
        assertNotNull(foundCustomer);
        assertEquals("8765432109", foundCustomer.getPersonalNumber());

        // Test not finding a customer
        assertNull(gymSystem.findCustomer("Non Existent"));
    }

    @Test
    public void testCategorizeCustomer() {
        // Test with a customer whose payment is within a year
        Customer currentMember = new Customer("1234567890", "John Doe", LocalDate.now().minusMonths(6));
        assertEquals("Nuvarande medlem", gymSystem.categorizeCustomer(currentMember));

        // Test with a customer whose payment is more than a year old
        Customer formerMember = new Customer("8765432109", "Jane Doe", LocalDate.now().minusYears(2));
        assertEquals("Ej nuvarande medlem", gymSystem.categorizeCustomer(formerMember));
    }

    @Test
    public void testPrintTrainingData() throws Exception {
        // Create a temporary file for training data
        Path trainingDataFile = tempDir.resolve("testTrainingData.txt");
        gymSystem.setTrainingDataFile(trainingDataFile.toString());

        // Create a test customer and print training data
        Customer customer = new Customer("1234567890", "John Doe", LocalDate.of(2023, 1, 1));
        gymSystem.printTrainingData(customer);

        // Read the content of the temp file
        List<String> lines = Files.readAllLines(trainingDataFile);
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("John Doe,1234567890," + LocalDate.now()));
        assertTrue(lines.get(0).contains(LocalDate.now().toString()));
    }

    @Test
    public void testMainMethod() throws Exception {
        // Redirect System.in to provide input
        String input = "7703021234\nq\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Capture System.out to verify output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the main method
        GymSystem.main(new String[]{});

        // Get the captured output as a string
        String output = outContent.toString();
        System.out.println("Test output: " + output);

        // Verify the output contains expected information
        assertTrue(output.contains("Välkommen till Best Gym Evers nya kundregister!"));
        assertTrue(output.contains("Kund: Alhambra Aromes"));
        assertTrue(output.contains("Personnummer: 7703021234"));
        assertTrue(output.contains("Senaste betalning: 2024-07-01"));
        assertTrue(output.contains("Kategori: Nuvarande medlem"));
        assertTrue(output.contains("Träningsdata registrerad."));

        // Verify training data was written to file
        Path trainingDataFile = Paths.get("src/training_data.txt");
        List<String> trainingData = Files.readAllLines(trainingDataFile);
        boolean foundEntry = false;
        for (String line : trainingData) {
            if (line.contains("Alhambra Aromes,7703021234," + LocalDate.now())) {
                foundEntry = true;
                break;
            }
        }
        assertTrue(foundEntry, "Training data should be recorded for Alhambra Aromes");

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);
    }
}
