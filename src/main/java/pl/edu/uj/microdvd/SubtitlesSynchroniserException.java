package pl.edu.uj.microdvd;

/* https://www.baeldung.com/java-new-custom-exception */
public class SubtitlesSynchroniserException extends Exception {
  public SubtitlesSynchroniserException(String message) {
    super(message);
  }

  public SubtitlesSynchroniserException(String message, Throwable cause) {
    super(message, cause);
  }
}
