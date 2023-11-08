package exercises.week38;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Task5 {

    public static void main(String[] args) {
        getCurrentTimeAndDate();
    }

    public static void getCurrentTimeAndDate() {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<String> time = executor.submit(() -> new SimpleDateFormat("HH:mm:ss").format(new Date()));
        Future<String> date = executor.submit(() -> new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        executor.shutdown();

        int count = 0;

        int finishedFirst = 0;
        while (!time.isDone() || !date.isDone()) {
            if (count ++ % 10_000 == 0) {
                System.out.println("waiting... " + count);
            }
            if (finishedFirst > 0) {
                continue;
            }

            if (time.isDone()) {
                finishedFirst = 1;
            } else if (date.isDone()) {
                finishedFirst = 2;
            }
        }

        String result = "time and date: " + time.resultNow() + " :: " + date.resultNow();

        System.out.println(result);

        if (finishedFirst == 1) {
            System.out.println("Time finished first");
        } else {
            System.out.println("Date finished first");
        }
    }
}
