package pl.edu.uj.textindexer.controller.index;

import pl.edu.uj.textindexer.controller.HTMLConstants;

import java.util.Collection;
import java.util.stream.Collectors;

public class IndexPrinter {

    public String print(Index index) {
        return HTMLConstants.HTML_DOCUMENT_BEGINNING
                + getIndexAsAHTMLParagraph(index)
                + HTMLConstants.HTML_DOCUMENT_ENDING;
    }

    private String getEmptyIndexInfo() {
        return "- empty index -";
    }

    private String getIndexAsAHTMLParagraph(Index index) {
        String indexInfo;
        if (index.isEmpty()) {
            indexInfo = getEmptyIndexInfo();
        } else {
            indexInfo = index.getItems()
                    .stream()
                    .map(item -> item.getIndex() + ": " + wordsToString(item.getWords()))
                    .collect(Collectors.joining(HTMLConstants.BREAK_LINE));
        }
        return getAsHTMLParagraph(indexInfo);
    }

    private String getAsHTMLParagraph(String text) {
        return HTMLConstants.PARAGRAPH_OPEN + text + HTMLConstants.PARAGRAPH_CLOSE;
    }


    private String wordsToString(Collection<String> words) {
        return String.join(", ", words);
    }
}
