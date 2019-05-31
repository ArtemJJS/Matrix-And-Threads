package by.anelkin.multithreading.matrix;

import org.apache.log4j.Logger;

import java.util.Arrays;
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
                instance = new Matrix((new MatrixCreator()).initMatrixSize());
                logger.info("Matrix instance created upon " + Thread.currentThread().getName() + " request.");
            }
        } finally {
            lock.unlock();
        }
        logger.debug(Thread.currentThread().getName() + " received matrix instance.");
        return instance;
    }

    public boolean fillDiagonalCell(int index, int value) {
        Cell currentCell = field[index][index];
        boolean isUpdated = false;
        if (currentCell.cellLock.tryLock()) {
            try {
                currentCell.cellValue = value;
                logger.debug(Thread.currentThread().getName() + " fill cell " + index + ":" + index);
                if (index != field.length - 1) {
                    phaser.arriveAndAwaitAdvance();
                } else {
                    phaser.forceTermination();
                    logger.debug(Thread.currentThread().getName() + " terminate phaser.");
                }
                isUpdated = true;
            } finally {
                currentCell.cellLock.unlock();
            }
        }
        return isUpdated;
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

    public int getCellValue(int row, int column) {
        return field[row][column].cellValue;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Cell[] row : field) {
            for (Cell cell : row) {
                result.append(String.valueOf(cell.cellValue));
                result.append("  ");
            }
            result.append("\n");
        }
        return result.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.equals(field, matrix.field);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }
}
