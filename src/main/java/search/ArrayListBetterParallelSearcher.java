package search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ArrayListBetterParallelSearcher<T> implements Searcher<T> {

    private List<T> content;
    private int threadCount = 8;

    @Override
    public void setContent(List<T> elements) {
        this.content = elements;
        threadCount = Math.min(elements.size(), threadCount);
    }

    @Override
    public boolean contains(T element) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Boolean>> futures = new ArrayList<>();

        int chunkSize = content.size() / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = (i == threadCount - 1) ? content.size() : start + chunkSize;
            futures.add(executor.submit(new ArraySearcherTask<>(element, content.subList(start, end))));
        }

        String t;

        try {
            for (Future<Boolean> future : futures) {
                if (future.get()) { // If one thread found the element, shut down others and return true
                    executor.shutdownNow();
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error during parallel search", e);
        } finally {
            if (!executor.isShutdown()) {
                executor.shutdown();
            }
        }
        return false;
    }
}

