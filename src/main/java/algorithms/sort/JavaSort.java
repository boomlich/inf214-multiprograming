package algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaSort implements Sorting {

    @Override
    public List<Integer> sort(List<Integer> list) {
        List<Integer> result = new ArrayList<>(list);
        Collections.sort(result);
        return result;
    }
}
