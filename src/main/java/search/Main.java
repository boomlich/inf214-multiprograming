package search;

import util.Generator;
import util.ListWithTarget;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static DecimalFormat formatter = new DecimalFormat("#, ###");

    public static void main(String[] args) {
        List<Searcher<Integer>> algorithmList = new ArrayList<>();
        algorithmList.add(new ArrayListSearcher<>());
        algorithmList.add(new LinkedListSearcher<>());
        algorithmList.add(new ArrayParallelSearcher<>());
        algorithmList.add(new ArrayListBetterParallelSearcher<>());

        int listSize = 1000;
        while(!algorithmList.isEmpty() && listSize < Math.pow(10, 8)) {
            List<ListWithTarget<Integer>> integerLists = generateNLists(10, listSize);
            runAlgorithms(algorithmList, integerLists);
            listSize*=10;
        }
    }

    private static List<ListWithTarget<Integer>> generateNLists(int n, int listSize) {
        System.out.println("---Generating Integer Lists---");

        Random random = new Random();

        List<ListWithTarget<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> list = Generator.generateList(listSize);
            Integer target = i % 2 == 0 ? list.get(random.nextInt(list.size())) : Generator.generateElementNotInList(list);
            result.add(new ListWithTarget<>(target, list));
        }

        System.out.printf("%slists generated with %selements each.%n%n", formatter.format(n),
                formatter.format(listSize));

        return result;
    }

    private static void runAlgorithms(List<Searcher<Integer>> algorithmList, List<ListWithTarget<Integer>> integerLists) {
        // Record time of each algorithm for every list
        List<Searcher<Integer>> slowAlgorithms = new ArrayList<>();
        System.out.println("---Processing Algorithms---");
        for (Searcher<Integer> algorithm : algorithmList) {

            long totalTimeElapsedMicro = 0;
            for (ListWithTarget<Integer> listWithTarget: integerLists) {
                List<Integer> elem = listWithTarget.list();
                algorithm.setContent(elem);

                totalTimeElapsedMicro += timeAlgorithm(algorithm, listWithTarget) / 1000;
            }

            double timeElapsedSeconds = (totalTimeElapsedMicro / 1000000.0);
            if(timeElapsedSeconds>4.5)
                slowAlgorithms.add(algorithm);
            String algorithmName = algorithm.getClass().getSimpleName();
            System.out.printf("%-27s| time elapsed: %10d microseconds (%f seconds)%n", algorithmName, totalTimeElapsedMicro,
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
    public static long timeAlgorithm(Searcher<Integer> algorithm, ListWithTarget<Integer> listWithTarget) {
        long startTime = System.nanoTime();
        algorithm.contains(listWithTarget.target());
        long endTime = System.nanoTime();
        long timeElapsed = (endTime - startTime);
        return timeElapsed;
    }
}