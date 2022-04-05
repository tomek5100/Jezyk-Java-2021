package pl.edu.uj.sender;

public class SenderException extends Exception {

  public SenderException(String message, Throwable cause) {
    super(message, cause);
  }

  public SenderException(String message) {
    super(message);
  }
}
