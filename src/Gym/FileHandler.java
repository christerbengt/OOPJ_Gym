package Gym;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileHandler {
    public List<String> readFromFile(String filename) throws IOException {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new IOException("Error reading file: " + filename, e);
        }
    }

    public void writeToFile(String filename, String data) throws IOException {
        try {
            Files.write(Paths.get(filename), (data + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IOException("Error writing to file: " + filename, e);
        }
    }

    public static boolean isValidCustomerData(String[] parts) {
        return parts.length == 3 &&
                parts[0].trim().matches("\\d{10}") &&
                !parts[1].trim().isEmpty() &&
                parts[2].trim().matches("\\d{4}-\\d{2}-\\d{2}");
    }
}