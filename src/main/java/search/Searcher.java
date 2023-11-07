package search;

import java.util.List;

public interface Searcher<T> {

    void setContent(List<T> elements);


    /**
     * Search the collection to see if it contains the element.
     *
     * @return index of the element, or -1 if not found
     */
    boolean contains(T element);
}
