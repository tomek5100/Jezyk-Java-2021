package pl.edu.uj.textindexer.controller.index;

import java.util.Collection;

public class Index {

    private final Collection<IndexItem> items;

    public Index(Collection<IndexItem> items) {
        this.items = items;
    }

    public Collection<IndexItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
