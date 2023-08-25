package algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSortParallel implements Sorting {

    @Override
    public List<Integer> sort(List<Integer> list) {
        List<Integer> listCopy = new ArrayList<>(list);
        Collections.shuffle(listCopy);

        QuickSortThread t1 = new QuickSortThread(listCopy);

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException ignore) {}

        return t1.getList();
    }


}
