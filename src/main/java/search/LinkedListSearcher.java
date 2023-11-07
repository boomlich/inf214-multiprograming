package search;

import java.util.LinkedList;
import java.util.List;

public class LinkedListSearcher<T> implements Searcher<T> {

    LinkedList<T> content;

    @Override
    public void setContent(List<T> elements) {
        content = new LinkedList<>(elements);
    }

    @Override
    public boolean contains(T element) {
        return content.contains(element);
    }
}
