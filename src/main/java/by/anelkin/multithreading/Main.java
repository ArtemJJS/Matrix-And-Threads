package by.anelkin.multithreading;

import by.anelkin.multithreading.matrix.Matrix;
import by.anelkin.multithreading.reader.DataReader;
import by.anelkin.multithreading.thread.MatrixThread;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DataReader reader = new DataReader();
        List<String> data = reader.read("");
        Matrix.setMatrixSize(Integer.parseInt(data.get(0)));

        int count = data.size()-1;
        CyclicBarrier barrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 1; i <= count ; i++) {
            executorService.submit(new MatrixThread(Integer.parseInt(data.get(i)), barrier));
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        System.out.println(Matrix.getInstance());

    }
}
