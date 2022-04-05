package pl.edu.uj.microdvd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  /**
   * Example usage: /home/mmozolewski/Documents/Doktorat/java2021/src/main/resources/gravity.txt
   * /home/mmozolewski/Documents/Doktorat/java2021/src/main/resources/gravity-delayed.txt -500 30
   */
  public static void main(String[] args) {
    if (args.length != 4) {
      final Optional<String> parameters = Arrays.stream(args).reduce(String::concat);
      logger.error(
          MessageFormat.format(
              "Params should be: [input_file] [output_file] [delay (milliseconds)] [FPS] but got ''{0}''",
              parameters.orElse("no parameters")));
      System.exit(-1);
    }
    try {
      String inputFile = args[0];
      String outputFile = args[1];
      int delay = Integer.parseInt(args[2]);
      int fps = Integer.parseUnsignedInt(args[3]);
      SubtitlesSynchroniser.delay(inputFile, outputFile, delay, fps);
    } catch (SubtitlesSynchroniserException | SubtitlesLineMalformedException e) {
      logger.error(
          MessageFormat.format(
              "Cannot process input-file= ''{0}'', output-file= ''{1}'', delay= {2}, fps= {3} because of error: ",
              args[0], args[1], args[2], args[3]),
          e);
    } catch (Exception ex) {
      logger.error(
          MessageFormat.format(
              "Unknown error occurred for parameters input-file= ''{0}'', output-file= ''{1}'', delay= {2}, fps= {3}: ",
              args[0], args[1], args[2], args[3]),
          ex);
    }
  }
}
