package by.anelkin.multithreading.matrix;


import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Matrix {
    private static Lock lock = new ReentrantLock();
    private static int[][] field;
    private static int matrixSize;
    private static Matrix instance;


    private Matrix(int length) {
        field = new int[length][length];
    }

    public static Matrix getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new Matrix(matrixSize);
                instance.fillMatrix();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }


    public boolean fillDiagonalCell(int index, int value) {
        field[index][index] = value;
        return true;
    }

    private void fillMatrix() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i == j) {
                    field[i][j] = 0;
                } else {
                    field[i][j] = 0;
                }
            }
        }
    }

    public static boolean setMatrixSize(int matrixSize) {
        boolean isSizeSetted = false;
        if (field == null) {
            Matrix.matrixSize = matrixSize;
            isSizeSetted = true;
        }
        return isSizeSetted;
    }

    public static int getMatrixSize() {
        return matrixSize;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                result.append(field[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString().trim();
    }
}
