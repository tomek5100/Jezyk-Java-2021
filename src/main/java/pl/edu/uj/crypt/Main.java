package pl.edu.uj.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  /*
   * Usage:
   * 1. tocrypt.txt encrypt-rot.txt crypt rot
   * 2. decrypt.txt decrypt-rot.txt decrypt rot
   * 3. poli-input.txt encrypt-polib.txt crypt polib
   * 4. poli-output.txt decrypt-polib.txt decrypt polib
   * */
  public static void main(String[] args) {
    if (args.length != 4
        || (!args[2].equals("crypt") && !args[2].equals("decrypt"))
        || (!args[3].equals("rot") && !args[3].equals("polib"))) {
      throw new IllegalArgumentException(
          "Params should be: [input_file] [output_file] [crypt|decrypt] [rot|polib] but got '"
              + Arrays.stream(args).reduce(String::concat)
              + "'");
    }
    String inputFile = args[0];
    String outputFile = args[1];
    boolean crypt = args[2].equals("crypt");
    Algorithm algorithm = args[3].equals("rot") ? new ROT11() : new Polybius();

    logger.info(MessageFormat.format("Started {0}: {1} -> {2}", args[2], inputFile, outputFile));

    try {
      if (crypt) {
        Cryptographer.cryptfile(inputFile, outputFile, algorithm);
      } else {
        Cryptographer.decryptfile(inputFile, outputFile, algorithm);
      }
    } catch (IOException e) {
      logger.error(
          MessageFormat.format(
              "! Could not complete {0}: {1} -> {2}", args[2], inputFile, outputFile),
          e);
    }
  }
}
