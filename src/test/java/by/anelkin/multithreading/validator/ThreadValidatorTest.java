package by.anelkin.multithreading.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ThreadValidatorTest {
    private ThreadValidator validator = new ThreadValidator();

    @DataProvider(name = "testDataForValidator")
    public Object[][] testDataForValidator(){
        return new Object[][]{
                {"a", false},
                {"", false},
                {"1 2", false},
                {"1.44", false},
                {"1a", false},
                {"1", true},
                {"-14578", true}
        };
    }

    @Test(dataProvider = "testDataForValidator")
    public void testValidate(String line, boolean expected) {
        boolean actual = validator.validate(line);

        Assert.assertEquals(actual, expected);
    }
}