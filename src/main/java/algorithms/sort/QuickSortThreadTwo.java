package algorithms.sort;

import java.util.ArrayList;
import java.util.List;

public class QuickSortThreadTwo  extends Thread {

    private List<Integer> input;

    public QuickSortThreadTwo(List<Integer> input) {
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
        List<Integer> smaller = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();

        int pivot = input.get(0);

        for (int i = 1; i < input.size(); i++) {
            int element = input.get(i);

            if (element == pivot) {
                equal.add(element);
            }
            else if (element < pivot) {
                smaller.add(element);
            } else {
                greater.add(element);
            }
        }

        smaller = quickSort(smaller);
        greater = quickSort(greater);

        ArrayList<Integer> result = new ArrayList<>(smaller);
        result.add(pivot);
        result.addAll(equal);
        result.addAll(greater);

        return result;
    }
}
