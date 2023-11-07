package exercises.week36;

import java.util.concurrent.*;

public class Task2 {


    public static void main(String[] args) {

        // example of using the function `run_both`:

        // f1:
        Callable<Integer> f1 = () -> {
            Thread.sleep(1000);
            return 42;
        };

        // f2:
        Callable<String> f2 = () -> "Greetings INF214!";

        try {
            Tuple<Integer, String> res = run_both(f1, f2);
            System.out.println("Result of function1: " + res.first());
            System.out.println("Result of function2: " + res.second());
        }
        catch (InterruptedException | ExecutionException ignored) { }
    }

    public static <T1, T2> Tuple<T1, T2> run_both(Callable<T1> f1, Callable<T2> f2) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Run the two functions `f1` and `f2`
        Future<T1> run1 = executor.submit(f1);
        Future<T2> run2 = executor.submit(f2);

        // Wait for the both runs to complete
        T1 result1 = run1.get();
        T2 result2 = run2.get();

        return new Tuple<>(result1, result2);

    }

}
