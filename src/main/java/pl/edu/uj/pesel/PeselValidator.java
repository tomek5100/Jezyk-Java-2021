package pl.edu.uj.pesel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * PESEL validator
 */
public class PeselValidator {

  private static final Logger logger = LoggerFactory.getLogger(PeselValidator.class);

  public static void main(String[] args) {
    for (String arg : args) {
      Pesel pesel = new Pesel(arg);
      boolean valid = Pesel.check(pesel);
      logger.info(MessageFormat.format("PESEL \"{0}\" is {1}", arg, valid ? "valid" : "invalid"));
    }
  }
}
