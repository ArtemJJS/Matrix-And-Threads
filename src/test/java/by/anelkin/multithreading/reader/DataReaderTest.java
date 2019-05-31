package by.anelkin.multithreading.reader;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.*;

public class DataReaderTest {
    private DataReader reader = new DataReader();

    @BeforeMethod
    public void setUp() {
    }

    @Test
    public void testRead() {
        int expected = 0;
        try {
            expected = Files.readAllLines(Paths.get("src/main/resources/data/matrix_size")).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int actual = reader.read("src/main/resources/data/matrix_size").size();

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testReadDefault() {
        int expected = 0;
        try {
            expected = Files.readAllLines(Paths.get("src/main/resources/data/thread")).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int actual = reader.read("").size();

        Assert.assertEquals(actual,expected);
    }

}