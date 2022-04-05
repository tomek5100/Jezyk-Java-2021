package pl.edu.uj.microdvd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesLine {

  private static final Logger logger = LoggerFactory.getLogger(SubtitlesLine.class);

  public static final String LINE_DOES_NOT_CONFORM_TO_STANDARD =
      "Line ''{0}'' does not conform to standard";
  public static final String INVALID_FRAMES_START_STOP_IN_LINE =
      "Invalid milliseconds start= ''{0}'', stop= ''{1}'' in line ''{2}''";
  // https://regex101.com/r/B7zBuE/1
  static final Pattern PATTERN =
      Pattern.compile("^\\{(\\d+)}\\{(\\d+)}(?!\\{\\d+})(.*)$", Pattern.UNICODE_CASE);

  final long startFrame;
  final long stopFrame;
  final String textToPrint;

  protected SubtitlesLine(long startFrame, long stopFrame, String textToPrint, String line)
      throws SubtitlesLineMalformedException {
    this.startFrame = startFrame;
    this.stopFrame = stopFrame;
    if (startFrame < 0 || stopFrame < 0 || startFrame > stopFrame) {
      throw new SubtitlesLineMalformedException(
          MessageFormat.format(INVALID_FRAMES_START_STOP_IN_LINE, startFrame, stopFrame, line));
    }
    this.textToPrint = textToPrint;
  }

  protected static SubtitlesLine parse(String line) throws SubtitlesLineMalformedException {
    try {
      final Matcher matcher = PATTERN.matcher(line);
      if (matcher.find()) {
        if (logger.isDebugEnabled()) {
          logger.debug(MessageFormat.format("Full match: {0}", matcher.group(0)));
        }

        final String group1 = matcher.group(1);
        final String group2 = matcher.group(2);
        final String group3 = matcher.group(3);

        long start = Long.parseLong(group1);
        long stop = Long.parseLong(group2);
        if (start < 0 || stop < 0 || start > stop) {
          throw new SubtitlesLineMalformedException(
              MessageFormat.format(INVALID_FRAMES_START_STOP_IN_LINE, start, stop, line));
        }
        return new SubtitlesLine(start, stop, group3, line);
      } else {
        throw new SubtitlesLineMalformedException(
            MessageFormat.format(LINE_DOES_NOT_CONFORM_TO_STANDARD, line));
      }
    } catch (Exception e) {
      throw new SubtitlesLineMalformedException(
          MessageFormat.format(LINE_DOES_NOT_CONFORM_TO_STANDARD, line), e);
    }
  }

  public long getStartFrame() {
    return startFrame;
  }

  public long getStopFrame() {
    return stopFrame;
  }

  public String getTextToPrint() {
    return textToPrint;
  }

  public String generateLine() {
    return "{" + startFrame + "}{" + stopFrame + "}" + textToPrint;
  }
}
