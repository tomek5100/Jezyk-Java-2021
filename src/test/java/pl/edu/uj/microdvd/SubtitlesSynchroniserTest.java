package pl.edu.uj.microdvd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * See https://blogs.oracle.com/javamagazine/post/working-and-unit-testing-with-temporary-files-in-java
 */
class SubtitlesSynchroniserTest {
  Path pathInputSubtitles, pathOutputSubtitles;

  /* This directory and the files created in it will be deleted after
   * tests are run, even in the event of failures or exceptions.
   */
  @TempDir Path tempDir;

  /* executed before every test */
  @BeforeEach
  public void setUp() throws IOException {
    pathInputSubtitles = tempDir.resolve("input.txt");
    pathOutputSubtitles = tempDir.resolve("output.txt");

    FileWriter fw1 = new FileWriter(pathInputSubtitles.toFile());
    BufferedWriter bw1 = new BufferedWriter(fw1);
    bw1.write(
            """
                    {328}{527}/Na wysokości 600 kilometrów temperatura waha się|/między 126 a -100 stopniami Celsjusza.
                    {544}{681}/Dźwięk się nie niesie.|/Nie ma ciśnienia ani tlenu.
                    {755}{858}/Życie w kosmosie jest niemożliwe.
                    {875}{949}GRAWITACJA
                    {954}{1014}.:: GrupaHatak.pl ::.
                    {1017}{1081}Tłumaczenie:|rarehare & józek""");
    bw1.close();
  }

  @Test
  void test_subtitles_synchroniser()
      throws IOException, SubtitlesSynchroniserException, SubtitlesLineMalformedException {
    SubtitlesSynchroniser.delay(
        pathInputSubtitles.toFile().getPath(), pathOutputSubtitles.toFile().getPath(), 500, 60);
    final List<String> outputFileLines = Files.readAllLines(pathOutputSubtitles);
    assertEquals(6, outputFileLines.size());
    assertEquals(
        "{359}{558}/Na wysokości 600 kilometrów temperatura waha się|/między 126 a -100 stopniami Celsjusza.",
        outputFileLines.get(0));
    assertEquals(
        "{575}{712}/Dźwięk się nie niesie.|/Nie ma ciśnienia ani tlenu.", outputFileLines.get(1));
    assertEquals("{786}{889}/Życie w kosmosie jest niemożliwe.", outputFileLines.get(2));
    assertEquals("{906}{980}GRAWITACJA", outputFileLines.get(3));
    assertEquals("{985}{1045}.:: GrupaHatak.pl ::.", outputFileLines.get(4));
    assertEquals("{1048}{1112}Tłumaczenie:|rarehare & józek", outputFileLines.get(5));
  }

  @Test
  void test_subtitles_synchronised_not_existent_input()       {
    assertThrows(
        SubtitlesSynchroniserException.class,
        () ->
            SubtitlesSynchroniser.delay(
                "not/existent/file", pathOutputSubtitles.toFile().getPath(), 500, 60));
  }
}
