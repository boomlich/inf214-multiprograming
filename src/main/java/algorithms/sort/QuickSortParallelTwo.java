package algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuickSortParallelTwo implements Sorting {

    private final Random random = new Random();
    private final int threadCount = 4;

    @Override
    public List<Integer> sort(List<Integer> list) {
        List<Integer> listCopy = new ArrayList<>(list);
        Collections.shuffle(listCopy);

        List<QSList> qsLists = divideQSList(divideInTwo(listCopy));

        List<QuickSortThreadTwo> threads = new ArrayList<>();
        threads.add(new QuickSortThreadTwo(qsLists.get(0).smaller()));
        threads.add(new QuickSortThreadTwo(qsLists.get(0).greaterOrEqual()));
        threads.add(new QuickSortThreadTwo(qsLists.get(1).smaller()));
        threads.add(new QuickSortThreadTwo(qsLists.get(1).greaterOrEqual()));

        for (QuickSortThreadTwo qsThread: threads) {
            qsThread.start();
        }

        for (QuickSortThreadTwo qsThread: threads) {
            try {
                qsThread.join();
            } catch (InterruptedException ignore) {}
        }

        List<Integer> result = new ArrayList<>(threads.get(0).getList());
        result.add(qsLists.get(0).pivot());
        result.addAll(threads.get(1).getList());
        result.addAll(threads.get(2).getList());
        result.add(qsLists.get(1).pivot());
        result.addAll(threads.get(3).getList());

        return result;
    }

    private QSList divideInTwo(List<Integer> inputList) {

        List<Integer> smaller = new ArrayList<>();
        List<Integer> largerOrEqual = new ArrayList<>();

        int pivot = inputList.get(0);

        for (int i = 1; i < inputList.size(); i++) {
            int element = inputList.get(i);
            if (element < pivot) {
                smaller.add(element);
            } else {
                largerOrEqual.add(element);
            }
        }

        return new QSList(pivot, smaller, largerOrEqual);
    }

    private List<QSList> divideQSList(QSList inputList) {
        List<QSList> result = new ArrayList<>();

        QSList smaller = divideInTwo(inputList.smaller());
        QSList greater = divideInTwo(inputList.greaterOrEqual());

        result.add(smaller);
        result.add(greater);
        return result;
    }
}
