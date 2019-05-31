package by.anelkin.multithreading.matrix;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MatrixTest {
    Matrix matrix;

    @BeforeMethod
    public void setUp() {
        matrix = Matrix.getInstance();
    }

    @Test
    public void testGetInstance() {
        Matrix expected = matrix;
        Matrix actual = Matrix.getInstance();

        Assert.assertEquals(actual, expected);
    }
}