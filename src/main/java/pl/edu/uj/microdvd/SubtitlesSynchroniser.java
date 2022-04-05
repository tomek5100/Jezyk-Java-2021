package pl.edu.uj.microdvd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;

public class SubtitlesSynchroniser {
  private static final Logger logger = LoggerFactory.getLogger(SubtitlesSynchroniser.class);

  private SubtitlesSynchroniser() {}

  static void delay(String in, String out, int delay, int fps)
      throws SubtitlesLineMalformedException, SubtitlesSynchroniserException {

    if (fps < 0) {
      throw new SubtitlesSynchroniserException(
          MessageFormat.format("Invalid parameter fps= {0}", fps));
    }

    final int frameInMilliseconds = 1000 / fps;
    final int delayInFrames = delay / frameInMilliseconds;
    if (logger.isDebugEnabled()) {
      logger.debug(
          MessageFormat.format(
              "frameInMilliseconds= {0}, delayInFrames= {1}", frameInMilliseconds, delayInFrames));
    }

    File input = new File(in);
    File output = new File(out);
    String line = null;
    try {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, false))) {
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
          boolean first = true;
          while ((line = br.readLine()) != null) {
            if (!first) {
              writer.newLine();
            } else {
              first = false;
            }
            final SubtitlesLine sLine = SubtitlesLine.parse(line);
            final SubtitlesLine newSLine =
                new SubtitlesLine(
                    sLine.getStartFrame() + delayInFrames,
                    sLine.getStopFrame() + delayInFrames,
                    sLine.getTextToPrint(),
                    line);
            writer.write(newSLine.generateLine());
          }
        }
      }
    } catch (SubtitlesLineMalformedException e) {
      throw e;
    } catch (Exception e) {
      throw new SubtitlesSynchroniserException(
          MessageFormat.format("Exception occurred in line ''{0}''", line), e);
    }
  }
}
