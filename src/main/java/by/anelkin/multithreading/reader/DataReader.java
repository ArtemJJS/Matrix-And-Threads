package by.anelkin.multithreading.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReader {
    private String DEFAULT_PATH = "src/main/resources/data/default_input";

    public List<String> read(String path) {
        if (path == null || !(new File(path).isFile())) {
            path = DEFAULT_PATH;
        }

        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
