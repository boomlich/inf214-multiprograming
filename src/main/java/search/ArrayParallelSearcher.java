package search;

import java.util.ArrayList;
import java.util.List;

public class ArrayParallelSearcher<T> implements Searcher<T> {

    private List<List<T>> contents;
    private int threadCount = 4;

    @Override
    public void setContent(List<T> elements) {
        contents = new ArrayList<>();

        threadCount = Math.min(elements.size(), threadCount);

        for (int i = 0; i < threadCount; i++) {
            contents.add(new ArrayList<>());
        }

        for (int i = 0; i < elements.size(); i++) {
            int threadList = i % contents.size();
            contents.get(threadList).add(elements.get(i));
        }
    }

    @Override
    public boolean contains(T element) {
        List<ArraySearcherThread<T>> threads = new ArrayList<>();

        for (List<T> subList: contents) {
            threads.add(new ArraySearcherThread<>(element, subList));
        }

        for (ArraySearcherThread<T> thread: threads) {
            thread.start();
        }

        for (ArraySearcherThread<T> thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {}
        }

        // Check if any of the thread found the element
        boolean hasFound = false;
        for (ArraySearcherThread<T> thread: threads) {
            if (thread.hasFound()) {
                hasFound = true;
                break;
            }
        }

        return hasFound;
    }
}
