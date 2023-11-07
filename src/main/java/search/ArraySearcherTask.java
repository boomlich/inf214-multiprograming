package search;

import java.util.List;
import java.util.concurrent.Callable;

class ArraySearcherTask<T> implements Callable<Boolean> {

    private final T target;
    private final List<T> content;

    public ArraySearcherTask(T target, List<T> content) {
        this.target = target;
        this.content = content;
    }

    @Override
    public Boolean call() throws Exception {
        for (T element : content) {
            if (Thread.currentThread().isInterrupted()) { // If interrupted, stop searching
                return false;
            }
            if (element.equals(target)) {
                return true;
            }
        }
        return false;
    }
}