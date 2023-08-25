package algorithms.sort;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final Random random = new Random();

    public static List<Integer> generateList(int n, int min, int max) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(random.nextInt(max * 2) - min);
        }

        return list;
    }

}
