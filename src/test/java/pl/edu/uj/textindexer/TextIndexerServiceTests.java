package pl.edu.uj.textindexer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.uj.textindexer.controller.index.Index;
import pl.edu.uj.textindexer.controller.index.IndexItem;
import pl.edu.uj.textindexer.service.TextIndexerService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TextIndexerService.class)
class TextIndexerServiceTests {

  private final Gson gson = (new GsonBuilder()).create();
  private final TextIndexerService indexer = new TextIndexerService();

  @Test
  public void givenExerciseExample_returnOutputProvidedByRecruiter() {
    Index index = indexer.getIndex("ala ma kota, kot koduje w Javie Kota");

    final IndexItem a = new IndexItem(
            'a', new ArrayList<>(Arrays.asList("ala", "javie", "kota", "ma")));
    final IndexItem d = new IndexItem('d', new ArrayList<>(Arrays.asList("koduje")));
    final IndexItem e = new IndexItem('e', new ArrayList<>(Arrays.asList("javie", "koduje")));
    final IndexItem i = new IndexItem('i', new ArrayList<>(Arrays.asList("javie")));
    final IndexItem j = new IndexItem('j', new ArrayList<>(Arrays.asList("javie", "koduje")));
    final IndexItem k = new IndexItem('k', new ArrayList<>(Arrays.asList("koduje", "kot", "kota")));
    final IndexItem l = new IndexItem('l', new ArrayList<>(Arrays.asList("ala")));
    final IndexItem m = new IndexItem('m', new ArrayList<>(Arrays.asList("ma")));
    final IndexItem o = new IndexItem('o', new ArrayList<>(Arrays.asList("koduje", "kot", "kota")));
    final IndexItem t = new IndexItem('t', new ArrayList<>(Arrays.asList("kot", "kota")));
    final IndexItem u = new IndexItem('u', new ArrayList<>(Arrays.asList("koduje")));
    final IndexItem v = new IndexItem('v', new ArrayList<>(Arrays.asList("javie")));
    final IndexItem w = new IndexItem('w', new ArrayList<>(Arrays.asList("w")));
    Index indexExpected = new Index(
            new ArrayList<>(Arrays.asList(a, d, e, i, j, k, l, m, o, t, u, v, w)));

    assertEquals(gson.toJson(indexExpected, Index.class), gson.toJson(index, Index.class));
  }

  @Test
  public void givenEmptyText_returnEmptyIndex() {
    Index index = indexer.getIndex("");

    Index indexExpected = new Index(new ArrayList<>());

    assertEquals(gson.toJson(indexExpected, Index.class), gson.toJson(index, Index.class));
  }

  @Test
  public void givenWordWithRepeatedCharter_putItOnceInIndexByLetter() {
    Index index = indexer.getIndex("mama");

    final IndexItem a = new IndexItem('a', new ArrayList<>(Arrays.asList("mama")));
    final IndexItem m = new IndexItem('m', new ArrayList<>(Arrays.asList("mama")));
    Index indexExpected = new Index(new ArrayList<>(Arrays.asList(a, m)));

    assertEquals(gson.toJson(indexExpected, Index.class), gson.toJson(index, Index.class));
  }

  @Test
  public void givenNumber_doNotPutItInIndex() {
    Index index = indexer.getIndex("ala 123");

    final IndexItem a = new IndexItem('a', new ArrayList<>(Arrays.asList("ala")));
    final IndexItem l = new IndexItem('l', new ArrayList<>(Arrays.asList("ala")));
    Index indexExpected = new Index(new ArrayList<>(Arrays.asList(a, l)));

    assertEquals(gson.toJson(indexExpected, Index.class), gson.toJson(index, Index.class));
  }

  @Test
  public void givenWordWithDigit_putItInIndex_and_indexOnlyByLetters() {
    Index index = indexer.getIndex("ala123");

    final IndexItem a = new IndexItem('a', new ArrayList<>(Arrays.asList("ala123")));
    final IndexItem l = new IndexItem('l', new ArrayList<>(Arrays.asList("ala123")));
    Index indexExpected = new Index(new ArrayList<>(Arrays.asList(a, l)));

    assertEquals(gson.toJson(indexExpected, Index.class), gson.toJson(index, Index.class));
  }

  @Test
  public void givenWordWithNonAlphabeticalOrderOfLetters_preserveAlphabeticalOrderOfIndexes() {
    Index index = indexer.getIndex("jadwiga");

    final IndexItem a = new IndexItem('a', new ArrayList<>(Arrays.asList("jadwiga")));
    final IndexItem d = new IndexItem('d', new ArrayList<>(Arrays.asList("jadwiga")));
    final IndexItem g = new IndexItem('g', new ArrayList<>(Arrays.asList("jadwiga")));
    final IndexItem i = new IndexItem('i', new ArrayList<>(Arrays.asList("jadwiga")));
    final IndexItem j = new IndexItem('j', new ArrayList<>(Arrays.asList("jadwiga")));
    final IndexItem w = new IndexItem('w', new ArrayList<>(Arrays.asList("jadwiga")));

    Index indexExpected = new Index(new ArrayList<>(Arrays.asList(a, d, g, i, j, w)));

    assertEquals(gson.toJson(indexExpected, Index.class), gson.toJson(index, Index.class));
  }
}
