package algorithms.sort;

import util.Generator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static DecimalFormat formatter = new DecimalFormat("#, ###");

    public static void main(String[] args) {
        List<Sorting> algorithmList = new ArrayList<>();
        algorithmList.add(new QuickSort());
        algorithmList.add(new QuickSortParallel());
        algorithmList.add(new QuickSortParallelTwo());
        algorithmList.add(new JavaSort());


        int listSize = 1000;
        while(!algorithmList.isEmpty()) {
            List<List<Integer>> integerLists = generateNLists(10, listSize);
            runAlgorithms(algorithmList, integerLists);
            listSize*=10;
        }
    }

    private static List<List<Integer>> generateNLists(int n, int listSize) {
        System.out.println("---Generating Integer Lists---");
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(Generator.generateList(listSize, 0, 100000));
        }

        System.out.printf("%slists generated with %selements each.%n%n", formatter.format(n),
                formatter.format(listSize));

        return result;
    }

    private static void runAlgorithms(List<Sorting> algorithmList, List<List<Integer>> integerLists) {
        // Run containsThree of each algorithm on the generated lists.
        // Record time of each algorithm for every list
        List<Sorting> slowAlgorithms = new ArrayList<>();
        System.out.println("---Processing Algorithms---");
        for (Sorting algorithm : algorithmList) {
            long timeElapsedMicro = timeAlgorithm(algorithm, integerLists) / 1000;
            double timeElapsedSeconds = (timeElapsedMicro / 1000000.0);
            if(timeElapsedSeconds>4.5)
                slowAlgorithms.add(algorithm);
            String algorithmName = algorithm.getClass().getSimpleName();
            System.out.printf("%-27s| time elapsed: %10d microseconds (%f seconds)%n", algorithmName, timeElapsedMicro,
                    timeElapsedSeconds);
        }
        System.out.println('\n');
        algorithmList.removeAll(slowAlgorithms);
    }



    /**
     * Runs the given <code>algorithm</code> on several lists
     * <code>integerLists</code> to find any occurrence of a triplicate. Records the
     * time spent to find/not find triplicates in all the lists.
     *
     * @param algorithm    findTriplicate algorithm
     * @param integerLists list of lists of integers to be searched for triplicates
     * @return long of nanoseconds spent
     */
    public static long timeAlgorithm(Sorting algorithm, List<List<Integer>> integerLists) {
        long startTime = System.nanoTime();
        for (List<Integer> integerList : integerLists) {
            algorithm.sort(integerList);
        }
        long endTime = System.nanoTime();
        long timeElapsed = (endTime - startTime);
        return timeElapsed;
    }
}
