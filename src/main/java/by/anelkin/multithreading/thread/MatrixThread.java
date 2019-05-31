package by.anelkin.multithreading.thread;

import by.anelkin.multithreading.matrix.Matrix;
import org.apache.log4j.Logger;

public class MatrixThread extends Thread {
    private static final Logger logger = Logger.getLogger(MatrixThread.class);
    private int value;
    private String name;

    public MatrixThread(int value) {
        this.name = "Thread#" + value;
        this.value = value;
    }

    @Override
    public void run() {
        logger.debug(name + " is starting.");
        Matrix matrix = Matrix.getInstance();
        for (int i = 0; i < matrix.getMatrixSize(); i++) {
          if (matrix.getCellValue(i,i) == 0) {
              matrix.fillDiagonalCell(i, value);
          }
        }
        logger.debug(name + " finished work.");
    }
}
