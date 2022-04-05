package pl.edu.uj.textindexer.controller;

// TODO make enumeration
public class HTMLConstants {

    private HTMLConstants() {
    }

    public static final String HTML_DOCUMENT_BEGINNING = "<!DOCTYPE html>\n<html>\n   <body>";
    public static final String HTML_DOCUMENT_ENDING = "\n   </body>\n</html>";
    public static final String PARAGRAPH_OPEN = "\n      <p>";
    public static final String PARAGRAPH_CLOSE = "</p>";
    public static final String BREAK_LINE = "\n         <br>";
}
