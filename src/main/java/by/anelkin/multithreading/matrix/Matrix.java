package by.anelkin.multithreading.matrix;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Matrix {
    private static final Logger logger = Logger.getLogger(Matrix.class);
    private static Lock lock = new ReentrantLock();
    private Cell[][] field;
    private static Matrix instance;
    private Phaser phaser;


    private Matrix(int length) {
        field = new Cell[length][length];
        fillMatrix();
        this.phaser = (new MatrixCreator()).initMatrixPhaser();
        logger.info("Matrix created. Size: " + field.length + ". Phaser counter: " + phaser.getUnarrivedParties());
    }

    public static Matrix getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new Matrix((new MatrixCreator()).initMatrix());
                logger.info("Matrix instance created upon " + Thread.currentThread().getName() + " request.");
            }
        } finally {
            lock.unlock();
        }
        logger.debug(Thread.currentThread().getName() + " received matrix instance.");
        return instance;
    }

    public boolean fillDiagonalCell(int index, int value) {
        boolean isUpdateDone = false;
        Cell currentCell = field[index][index];
        if (currentCell.cellLock.tryLock() && currentCell.cellValue == 0) {
            try {
                currentCell.cellValue = value;
                isUpdateDone = true;
                logger.debug(Thread.currentThread().getName() + " fill cell " + index + ":" + index);
                // without phaser we always have output like:  1 2 3 4 5 1 1 1 1 1 1
                if (index != field.length - 1) {
                    phaser.arriveAndAwaitAdvance();
                } else {
                    phaser.forceTermination();
                    logger.debug(Thread.currentThread().getName() + " terminate phaser.");
                }
            } finally {
                currentCell.cellLock.unlock();
            }
        }
        return isUpdateDone;
    }

    public int getMatrixSize() {
        return field.length;
    }

    private void fillMatrix() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i == j) {
                    field[i][j] = new Cell(0);
                } else {
                    field[i][j] = new Cell(new Random().nextInt(89) + 10);
                }
            }
        }
    }

    private static class Cell {
        private Lock cellLock = new ReentrantLock();
        private int cellValue;

        private Cell(int cellValue) {
            this.cellValue = cellValue;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                result.append(String.valueOf(field[i][j].cellValue));
                result.append("  ");
            }
            result.append("\n");
        }
        return result.toString().trim();
    }
}
