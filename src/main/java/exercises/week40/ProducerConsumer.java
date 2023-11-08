package exercises.week40;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    static int buf = 0;
    static Semaphore full = new Semaphore(0, true);
    static Semaphore empty = new Semaphore(1, true);
    static Random rand = new Random();

    public static void main(String[] args) {

        int consumers = 5;
        int producers = 5;

        ExecutorService executor = Executors.newFixedThreadPool(consumers + producers);

        for (int i = 0; i < producers; i++) {
            executor.submit(new Producer(i));
        }

        for (int i = 0; i < consumers; i++) {
            executor.submit(new Consumer(i));
        }

        executor.shutdown();

    }

    static class Producer implements Runnable {

        int i;

        public Producer(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while (true) {
                empty.acquireUninterruptibly();

                buf = rand.nextInt();
                System.out.println("PRODUCER " + i + " || PRODUCED:" + buf);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                full.release();
            }
        }
    }

    static class Consumer implements Runnable {
        int i;

        public Consumer(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while (true) {
                full.acquireUninterruptibly();

                int result = buf;
                buf = 0;
                System.out.println("CONSUMER " + i + " || CONSUMED " + result);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                empty.release();
            }
        }
    }


}
