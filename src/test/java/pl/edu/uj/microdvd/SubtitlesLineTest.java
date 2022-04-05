package pl.edu.uj.microdvd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubtitlesLineTest {

  @Test
  void test_valid_line() throws SubtitlesLineMalformedException {
    String line =
        "{1539}{1674}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.";
    final SubtitlesLine parse = SubtitlesLine.parse(line);

    assertEquals(1539L, parse.getStartFrame());
    assertEquals(1674L, parse.getStopFrame());
    assertEquals(
        "/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
        parse.getTextToPrint());
  }

  @Test
  void test_blank_text() throws SubtitlesLineMalformedException {
    String line = "{1539}{1674}";
    final SubtitlesLine parse = SubtitlesLine.parse(line);

    assertEquals(1539L, parse.getStartFrame());
    assertEquals(1674L, parse.getStopFrame());
    assertEquals("", parse.getTextToPrint());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(
      strings = {
        "Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
        "{1539}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
        "{1539}{1674}/First line.\n{2539}{2674}/Second line.",
        "{1539}{1674}{2274}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
      })
  void test_cannot_line_invalid(String invalidValue) {
    assertThrows(SubtitlesLineMalformedException.class, () -> SubtitlesLine.parse(invalidValue));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "{abc}{1674}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
        "{1539}{}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
      })
  void test_cannot_parse_frame_number(String invalidValue) {
    assertThrows(SubtitlesLineMalformedException.class, () -> SubtitlesLine.parse(invalidValue));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "{1674}{1539}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
        "{-1}{1674}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
        "{0}{-2}/Proszę o potwierdzenie, że pierwsza i druga|/część obudowy dysku P1 zostały usunięte.",
      })
  void test_invalid_frame_number(String invalidValue) {
    assertThrows(SubtitlesLineMalformedException.class, () -> SubtitlesLine.parse(invalidValue));
  }
}
