package by.anelkin.multithreading.matrix;

import by.anelkin.multithreading.reader.DataReader;

import java.util.List;
import java.util.concurrent.Phaser;

class MatrixCreator {
    private static final int DEFAULT_MATRIX_SIZE = 12;

    int initMatrix() {
        DataReader reader = new DataReader();
        List<String> matrixSize = reader.read("src/main/resources/data/matrix_size");
        try {
            return Integer.parseInt(matrixSize.get(0));
        } catch (NumberFormatException e) {
            //log
            return DEFAULT_MATRIX_SIZE;
        }
    }

    Phaser initMatrixPhaser(){
        DataReader reader = new DataReader();
        int threadCount = reader.read("src/main/resources/data/thread").size();
        return new Phaser(threadCount);
    }
}
