package by.anelkin.multithreading.matrix;

import by.anelkin.multithreading.reader.DataReader;
import by.anelkin.multithreading.validator.ThreadValidator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.Phaser;
import java.util.stream.Collectors;

class MatrixCreator {
    private static final Logger logger = Logger.getLogger(MatrixCreator.class);
    private static final int DEFAULT_MATRIX_SIZE = 12;
    private static final int DEFAULT_THREAD_COUNT = 1;
    private static final String MATRIX_SIZE_PATH = "src/main/resources/data/matrix_size";
    private static final String THREAD_PATH = "src/main/resources/data/thread";


    int initMatrixSize() {
        DataReader reader = new DataReader();
        List<String> matrixSize = reader.read(MATRIX_SIZE_PATH);
        try {
            return Integer.parseInt(matrixSize.get(0));
        } catch (NumberFormatException e) {
            logger.warn("Wrong input in " + MATRIX_SIZE_PATH + ". Size setted to default: " + DEFAULT_MATRIX_SIZE);
            return DEFAULT_MATRIX_SIZE;
        }
    }

    Phaser initMatrixPhaser() {
        DataReader reader = new DataReader();
        ThreadValidator validator = new ThreadValidator();
        int threadCount = reader.read(THREAD_PATH).stream()
                .filter(validator::validate).collect(Collectors.toList()).size();
        if (threadCount == 0) {
            threadCount = DEFAULT_THREAD_COUNT;
            logger.warn(THREAD_PATH + " is empty! Phase setted to default: " + threadCount);
        }
        return new Phaser(threadCount);
    }
}
