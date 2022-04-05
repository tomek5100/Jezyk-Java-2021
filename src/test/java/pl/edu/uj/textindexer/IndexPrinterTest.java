package pl.edu.uj.textindexer;

import org.junit.jupiter.api.Test;
import pl.edu.uj.textindexer.controller.HTMLConstants;
import pl.edu.uj.textindexer.controller.index.Index;
import pl.edu.uj.textindexer.controller.index.IndexItem;
import pl.edu.uj.textindexer.controller.index.IndexPrinter;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexPrinterTest {

    private final IndexPrinter printer = new IndexPrinter();

    @Test
    public void givenEmptyIndex_printEmptyIndexMessage() {
        Index index = new Index(new ArrayList<>());
        final String emptyIndexMessage = printer.print(index);

        final String expectedBody = HTMLConstants.HTML_DOCUMENT_BEGINNING
                + HTMLConstants.PARAGRAPH_OPEN + "- empty index -" + HTMLConstants.PARAGRAPH_CLOSE
                + HTMLConstants.HTML_DOCUMENT_ENDING;

        assertEquals(expectedBody, emptyIndexMessage);
    }

    @Test
    void givenEmptyIndex_print() {
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
        Index index = new Index(
                new ArrayList<>(Arrays.asList(a, d, e, i, j, k, l, m, o, t, u, v, w)));
        final String indexMessageForInputProvidedByRecruiter = printer.print(index);

        final String expectedBody = HTMLConstants.HTML_DOCUMENT_BEGINNING
                + HTMLConstants.PARAGRAPH_OPEN + "a: ala, javie, kota, ma"
                + HTMLConstants.BREAK_LINE + "d: koduje"
                + HTMLConstants.BREAK_LINE + "e: javie, koduje"
                + HTMLConstants.BREAK_LINE + "i: javie"
                + HTMLConstants.BREAK_LINE + "j: javie, koduje"
                + HTMLConstants.BREAK_LINE + "k: koduje, kot, kota"
                + HTMLConstants.BREAK_LINE + "l: ala"
                + HTMLConstants.BREAK_LINE + "m: ma"
                + HTMLConstants.BREAK_LINE + "o: koduje, kot, kota"
                + HTMLConstants.BREAK_LINE + "t: kot, kota"
                + HTMLConstants.BREAK_LINE + "u: koduje"
                + HTMLConstants.BREAK_LINE + "v: javie"
                + HTMLConstants.BREAK_LINE + "w: w"
                + HTMLConstants.PARAGRAPH_CLOSE
                + HTMLConstants.HTML_DOCUMENT_ENDING;

        assertEquals(expectedBody, indexMessageForInputProvidedByRecruiter);
    }
}