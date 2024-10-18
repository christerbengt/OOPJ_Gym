package Gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path customerFile = tempDir.resolve("testCustomers.txt");
        Files.writeString(customerFile, "1234567890, John Doe, 2023-01-01\n8765432109, Jane Doe, 2022-06-01");
        gymSystem.setCustomerFile(customerFile.toString());


        // Read the file and test the result
        List<Customer> customers = gymSystem.readCustomerFile();

        assertEquals(2, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
        assertEquals("8765432109", customers.get(1).getPersonalNumber());
        assertEquals(customerFile.toString(), gymSystem.getCustomerFile());

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
        Customer  formerMember = new Customer("8765432109", "Jane Doe", LocalDate.now().minusYears(2));
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
        Path customerFile = tempDir.resolve("testCustomers.txt");
        Files.writeString(customerFile, "1234567890, John Doe, " + LocalDate.now().minusMonths(6));
        gymSystem.setCustomerFile(customerFile.toString());

        Path trainingDataFile = tempDir.resolve("testTrainingData.txt");
        gymSystem.setTrainingDataFile(trainingDataFile.toString());

        String input = "John Doe\nq\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        GymSystem.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Kund: John Doe"));
        assertTrue(output.contains("Kategori: Nuvarande medlem"));
        assertTrue(output.contains("Träningsdata registrerad."));

        List<String> trainingData = Files.readAllLines(trainingDataFile);
        assertEquals(1, trainingData.size());
        assertTrue(trainingData.get(0).contains("John Doe,1234567890," + LocalDate.now()));
    }
}
