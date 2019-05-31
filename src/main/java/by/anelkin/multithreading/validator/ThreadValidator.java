package by.anelkin.multithreading.validator;

import org.apache.log4j.Logger;

public class ThreadValidator {
    private static final Logger logger = Logger.getLogger(ThreadValidator.class);

    public boolean validate(String line){
        if(line == null){
            return false;
        }
        boolean isCorrectValue = false;
        try{
            Integer.parseInt(line);
            isCorrectValue = true;
        }catch (NumberFormatException e){
            logger.debug("Incorrect line detected.");
        }
        return isCorrectValue;
    }
}
