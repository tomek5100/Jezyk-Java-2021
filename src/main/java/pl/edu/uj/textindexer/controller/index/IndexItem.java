package pl.edu.uj.textindexer.controller.index;

import java.util.Collection;

public class IndexItem {

    private final Character index;
    private final Collection<String> words;

    public IndexItem(Character index, Collection<String> words) {
        this.index = index;
        this.words = words;
    }

    public Character getIndex() {
        return index;
    }

    public Collection<String> getWords() {
        return words;
    }

    public boolean containsWord(String word) {
        return words.contains(word);
    }

    public void addWord(String word) {
        words.add(word);
    }
}
