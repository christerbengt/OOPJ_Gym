package Gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
        // This test assumes you have a test file with known content
        String testFilePath = "test_kunder.txt";
        GymSystem gymSystem = new GymSystem();
        List<Customer> customers = gymSystem.readCustomerFile(testFilePath);

        assertFalse(customers.isEmpty());
        assertEquals("7502031234", customers.get(0).getPersonalNumber());
        assertEquals("Anna Andersson", customers.get(0).getName());
        assertEquals(LocalDate.of(2023, 5, 3), customers.get(0).getLatestPayment());
    }

    @Test
    public void testFindCustomer() throws Exception {
        GymSystem gymSystem = new GymSystem();
        gymSystem.readCustomerFile("test_kunder.txt");

        Customer foundCustomer = gymSystem.findCustomer("7502031234");
        assertNotNull(foundCustomer);
        assertEquals("Anna Andersson", foundCustomer.getName());

        Customer notFound = gymSystem.findCustomer("0000000000");
        assertNull(notFound);
    }

    @Test
    public void testCategorizeCustomer() {
        LocalDate thisDay = LocalDate.now();
        Customer currentCustomer = new Customer("1234567890", "Test Customer", thisDay.minusMonths(6));
        Customer formerCustomer = new Customer("1234567890", "Test Customer", thisDay.minusYears(2));

        assertEquals("Current customer", gymSystem.categorizeCustomer(currentCustomer));
        assertEquals("Former customer", gymSystem.categorizeCustomer(formerCustomer));
        assertNull(gymSystem.categorizeCustomer(null));
    }
    @Test
    public void testPrintTrainingData() throws Exception {
        Path tempfile = tempDir.resolve("training_data.txt");
        Customer customer = new Customer("1234567890", "Test Customer", LocalDate.now().minusMonths(6));

        gymSystem.printTrainingData(customer, tempfile.toString());

        List<String> lines = Files.readAllLines(tempfile);
        assertFalse(lines.isEmpty());
        assertTrue(lines.get(0).contains("Test Customer"));
        assertTrue(lines.get(0).contains("1234567890"));
        assertTrue(lines.get(0).contains(LocalDate.now().toString()));
    }
}