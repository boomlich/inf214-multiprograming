package algorithms.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuickSortThread extends Thread {

    private List<Integer> input;

    public QuickSortThread(List<Integer> input) {
        this.input = input;
    }

    @Override
    public void run() {
        input = quickSort(input);
    }

    public List<Integer> getList() {
        return input;
    }

    private List<Integer> quickSort(List<Integer> input) {
        if (input.size() < 2) return input;

        List<Integer> greater = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> smaller = new ArrayList<>();

        int pivot = input.get(0);

        for (int i = 1; i < input.size(); i++) {
            int element = input.get(i);

            if (element == pivot) {
                equal.add(element);
            } else if (element < pivot) {
                smaller.add(element);
            } else {
                greater.add(element);
            }
        }

        int sizeLimit = 50000;

        QuickSortThread t1 = null;
        QuickSortThread t2 = null;

        if (smaller.size() < sizeLimit) {
            smaller = quickSort(smaller);
        } else {
            t1 = new QuickSortThread(smaller);
            t1.start();
        }

        if (greater.size() < sizeLimit) {
            greater = quickSort(greater);
        } else {
            t2 = new QuickSortThread(greater);
            t2.start();
        }

        if (t1 != null) {
            try {
                t1.join();
            } catch (InterruptedException ignore) {}
            smaller = t1.getList();
        }

        if (t2 != null) {
            try {
                t2.join();
            } catch (InterruptedException ignore) {}
            greater = t2.getList();
        }

        ArrayList<Integer> result = new ArrayList<>(smaller);
        result.add(pivot);
        result.addAll(equal);
        result.addAll(greater);

        return result;
    }
}
