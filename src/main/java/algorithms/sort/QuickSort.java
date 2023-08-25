package algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuickSort implements Sorting {

    private final Random random = new Random();

    @Override
    public List<Integer> sort(List<Integer> list) {
        Collections.shuffle(list);
        return quickSort(list);
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
