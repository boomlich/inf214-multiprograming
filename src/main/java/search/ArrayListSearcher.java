package search;

import java.util.ArrayList;
import java.util.List;

public class ArrayListSearcher<T> implements Searcher<T> {

    private ArrayList<T> content;

    @Override
    public void setContent(List<T> elements) {
        content = new ArrayList<>(elements);
    }

    @Override
    public boolean contains(T element) {
        return content.contains(element);
    }
}
