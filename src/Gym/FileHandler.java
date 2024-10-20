package Gym;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

// Class for handling file operations in the gym system.
public class FileHandler {
    // Reads all lines from a specified file.
    public List<String> readFromFile(String filename) throws IOException {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new IOException("Error reading file: " + filename, e);
        }
    }
    // writes a line of data to a specified file.
    public void writeToFile(String filename, String data) throws IOException {
        try {
            // Adding data to the file, creating it if it doesn't exist.
            Files.write(Paths.get(filename), (data + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IOException("Error writing to file: " + filename, e);
        }
    }
}