package by.anelkin.multithreading;

import by.anelkin.multithreading.matrix.Matrix;
import by.anelkin.multithreading.reader.DataReader;
import by.anelkin.multithreading.thread.MatrixThread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DataReader reader = new DataReader();
        List<String> data = reader.read("");

        int threadCount = data.size();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < data.size() ; i++) {
            executorService.submit(new MatrixThread(Integer.parseInt(data.get(i))));
        }
        executorService.shutdown();
        executorService.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println(Matrix.getInstance());
    }
}
