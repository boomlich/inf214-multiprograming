package exercises.week38;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task4 {

    public static void main(String[] args) {
        generateThreads(10);
    }

    public static void generateThreads(int n) {
        ExecutorService executor = Executors.newFixedThreadPool(n);

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                executor.submit(() -> System.out.println("Hello"));
            } else {
                executor.submit(() -> System.out.println("World"));
            }
        }

        executor.shutdown();
    }
}
