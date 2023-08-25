package algorithms.sort;

import java.util.List;

public record QSList(int pivot, List<Integer> smaller, List<Integer> greaterOrEqual) {
}
