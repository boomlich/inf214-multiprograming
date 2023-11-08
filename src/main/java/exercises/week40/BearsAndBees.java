package exercises.week40;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class BearsAndBees {

    static Semaphore mutex = new Semaphore(1, true);
    static Semaphore pot_full = new Semaphore(0);
    static Semaphore pot_empty = new Semaphore(0);
    static int honeyPot = 0;
    static int honeyLimit = 10;

    static int count = 0;

    public static void main(String[] args)  {
        int N = 4;

        ExecutorService executor = Executors.newFixedThreadPool(N + 1);

        executor.submit(() -> {
            try {
                bear();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        for (int i = 0; i < N; i++) {
            executor.submit(() -> {
                try {
                    bee();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
    }

    private static void bear() throws InterruptedException {
        while (true) {
            pot_full.acquire();

            honeyPot = 0;
            System.out.println("Bear || Eating...");
            Thread.sleep(1000);
            System.out.println("Bear || Done eating");

            pot_empty.release();
        }
    }

    private static void bee() throws InterruptedException {
        int i = count();
        while (true) {
            mutex.acquire();

            // Check if honey is full. If full -> wake bear and wait for it to be empty
            if (honeyPot == honeyLimit) {
                pot_full.release();
                pot_empty.acquire();
            }

            // Fill honeypot
            System.out.println("Bee " + i + " || Filling...");
            Thread.sleep(1000);
            honeyPot ++;
            System.out.println("Bee " + i + " || Done filling");

            mutex.release();
        }
    }

    private static synchronized int count() {
        return count ++;
    }
}
