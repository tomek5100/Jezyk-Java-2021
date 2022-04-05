package pl.edu.uj.crypt;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Cryptographer {

  private Cryptographer() {}

  public static void cryptfile(String pathToFileIn, String pathToFileOut, Algorithm algorithm)
      throws IOException {
    cryptOrDecrypt(pathToFileIn, pathToFileOut, algorithm, false);
  }

  public static void decryptfile(String pathToFileIn, String pathToFileOut, Algorithm algorithm)
      throws IOException {
    cryptOrDecrypt(pathToFileIn, pathToFileOut, algorithm, true);
  }

  private static void cryptOrDecrypt(
      String pathToFileIn, String pathToFileOut, Algorithm algorithm, boolean decrypt)
      throws IOException {

    File input = new File(pathToFileIn);
    File output = new File(pathToFileOut);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, false))) {
      try (BufferedReader br = new BufferedReader(new FileReader(input))) {
        String line;
        while ((line = br.readLine()) != null) {
          final List<String> words = Arrays.asList(line.split("\\s+"));
          ListIterator<String> itWords = words.listIterator();
          while (itWords.hasNext()) {
            final String word = itWords.next();
            String cryptoWord = decrypt ? algorithm.decrypt(word) : algorithm.crypt(word);
            writer.write(cryptoWord);
            if (itWords.hasNext()) {
              writer.write(" ");
            }
          }
          writer.newLine();
        }
      }
    }
  }
}
