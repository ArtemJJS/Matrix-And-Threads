package by.anelkin.multithreading.reader;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReader {
    private static final Logger logger = Logger.getLogger(DataReader.class);
    private static final String DEFAULT_PATH = "src/main/resources/data/thread";

    public List<String> read(String path) {
        logger.info("Start reading from " + path);
        if (path == null || !(new File(path).isFile())) {
            logger.warn("Path " + path + " changed to default " + DEFAULT_PATH);
            path = DEFAULT_PATH;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            logger.info("Read " + lines.size() + " lines from " + path);
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
