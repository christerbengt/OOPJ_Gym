package Gym;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileHandlerTest {

    @Test
    public void testReadFromFile() throws IOException {
        // Create a temporary file with test data
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, "7502031234, Anna Andersson, 2023-05-03".getBytes());

        FileHandler fileHandler = new FileHandler();
        List<String> lines = fileHandler.readFromFile(tempFile.toString());

        assertEquals(1, lines.size());
        assertEquals("7502031234, Anna Andersson, 2023-05-03", lines.get(0));

        // Clean up
        Files.delete(tempFile);
    }

    @Test
    public void testWriteToFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");

        FileHandler fileHandler = new FileHandler();
        fileHandler.writeToFile(tempFile.toString(), "Test data");

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(1, lines.size());
        assertEquals("Test data", lines.get(0));

        // Clean up
        Files.delete(tempFile);
    }
}