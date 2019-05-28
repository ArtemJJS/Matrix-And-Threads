package by.anelkin.multithreading.thread;

import by.anelkin.multithreading.matrix.Matrix;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixThread extends Thread {
    private int value;
    private String name;
    private static int index;
    private Lock lock = new ReentrantLock();
    private CyclicBarrier barrier;

    public MatrixThread(int value, CyclicBarrier barrier) {
        this.name = "Thread#" + value;
        this.value = value;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        Matrix matrix = Matrix.getInstance();
        while (index < Matrix.getMatrixSize()) {
            lock.lock();
            matrix.fillDiagonalCell(index++, value);
            try {
                barrier.await(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                //log
            } finally {
                lock.unlock();
            }

        }
        System.out.println(name + ":  index = " + index);
    }
}
