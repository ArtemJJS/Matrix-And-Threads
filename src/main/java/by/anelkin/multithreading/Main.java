package by.anelkin.multithreading;

import by.anelkin.multithreading.matrix.Matrix;
import by.anelkin.multithreading.reader.DataReader;
import by.anelkin.multithreading.thread.MatrixThread;
import by.anelkin.multithreading.validator.ThreadValidator;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DataReader reader = new DataReader();
        List<String> threads = reader.read("");

        int threadCount = threads.size();
        ThreadValidator validator = new ThreadValidator();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (String thread : threads) {
            if (validator.validate(thread)) {
                executorService.submit(new MatrixThread(Integer.parseInt(thread)));
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println(Matrix.getInstance());
    }
}
