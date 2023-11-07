package exercises.week38;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Task3 {

    public static void main(String[] args) {

        AtomicInteger counter = new AtomicInteger(0);

        class Counter implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 10_000; i++) {
                    counter.incrementAndGet();
                }
                System.out.println("DONE: " + counter.get());
            }
        }

        Runnable r1 = new Counter();
        Runnable r2 = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            executor.submit(r1);
            executor.submit(r2);

            executor.shutdown();
        } finally {
            System.out.println("FINALLY: " + counter.get());
        }

        System.out.println("after: " + counter.get());
    }


}
