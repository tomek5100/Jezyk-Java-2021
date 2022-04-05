package pl.edu.uj.microdvd;

/* https://www.baeldung.com/java-new-custom-exception */
public class SubtitlesLineMalformedException extends Exception {
  public SubtitlesLineMalformedException(String message) {
    super(message);
  }

  public SubtitlesLineMalformedException(String message, Throwable cause) {
    super(message, cause);
  }
}
