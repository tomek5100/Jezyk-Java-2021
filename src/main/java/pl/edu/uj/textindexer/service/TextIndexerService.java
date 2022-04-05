package pl.edu.uj.textindexer.service;

import org.springframework.stereotype.Service;
import pl.edu.uj.textindexer.controller.index.Index;
import pl.edu.uj.textindexer.controller.index.IndexItem;
import pl.edu.uj.textindexer.controller.index.IndexPrinter;

import java.util.*;

@Service("textIndexerService")
public class TextIndexerService {

  private final IndexPrinter printer = new IndexPrinter();

  public String printIndex(final String input) {
    final Index index = getIndex(input);
    return printer.print(index);
  }

  public Index getIndex(final String input) {
    String[] words = input.split("\\W+");

    if (words.length == 0) {
      return getEmptyIndex();
    }

    Collection<String> uniqueLowerCaseSortedWords = getUniqueLowerCaseSortedWords(words);
    Map<Character, IndexItem> indexItemsByIndex = getIndexItemsByIndexLetter(
            uniqueLowerCaseSortedWords);
    List<IndexItem> indexItems = new ArrayList<>(indexItemsByIndex.values());

    return new Index(indexItems);
  }

  private Map<Character, IndexItem> getIndexItemsByIndexLetter(Collection<String> sortedWords) {
    Map<Character, IndexItem> indexItemsByIndex = new TreeMap<>();
    for (String word : sortedWords) {
      for (int i = 0; i < word.length(); i++) {
        char character = word.charAt(i);
        if (Character.isLetter(character)) {
          indexItemsByIndex.putIfAbsent(character, new IndexItem(character, new ArrayList<>()));
          indexItemsByIndex.computeIfPresent(character, (letter, indexItem) -> {
            if (!indexItem.containsWord(word)) {
              indexItem.addWord(word);
            }
            return indexItem;
          });
        }
      }
    }
    return indexItemsByIndex;
  }

  private Collection<String> getUniqueLowerCaseSortedWords(String[] words) {
    return Arrays.stream(words)
            .map(String::toLowerCase)
            .distinct()
            .sorted(String::compareTo)
            .toList();
  }

  private Index getEmptyIndex() {
    return new Index(new ArrayList<>(0));
  }

}
