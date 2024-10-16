package Gym;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileHandler {
    public List<String> readFromFile(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    public void writeToFile(String filename, String data) throws IOException {
        Files.write(Paths.get(filename), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
