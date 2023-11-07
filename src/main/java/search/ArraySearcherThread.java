package search;

import java.util.List;

public class ArraySearcherThread<T> extends Thread {

    private final T target;
    private final List<T> content;

    private boolean hasFound = false;

    public ArraySearcherThread(T target, List<T> objects) {
        this.target = target;
        this.content = objects;
    }

    @Override
    public void run() {
        hasFound = false;

        for (T element : content) {
            if (element.equals(target)) {
                hasFound = true;
                return;
            }
        }
    }

    public boolean hasFound() {
        return hasFound;
    }
}
