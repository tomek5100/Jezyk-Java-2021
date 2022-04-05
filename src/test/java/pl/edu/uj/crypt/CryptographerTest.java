package pl.edu.uj.crypt;

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

/*
 * See https://blogs.oracle.com/javamagazine/post/working-and-unit-testing-with-temporary-files-in-java
 */
public class CryptographerTest {
  Path pathPlainText, pathEncryptedText, pathDecryptedText;

  /* This directory and the files created in it will be deleted after
   * tests are run, even in the event of failures or exceptions.
   */
  @TempDir Path tempDir;

  /* executed before every test */
  @BeforeEach
  public void setUp() throws IOException {
    pathPlainText = tempDir.resolve("testfile1.txt");
    pathEncryptedText = tempDir.resolve("testfile2.txt");
    pathDecryptedText = tempDir.resolve("testfile3.txt");

    FileWriter fw1 = new FileWriter(pathPlainText.toFile());
    BufferedWriter bw1 = new BufferedWriter(fw1);
    bw1.write("Ala\n" + "ma\n" + "kota.\n" + "I 12 lat.");
    bw1.close();
  }

  @Test
  public void testROT11Encryption() throws IOException {
    Cryptographer.cryptfile(
        pathPlainText.toFile().getPath(), pathEncryptedText.toFile().getPath(), new ROT11());
    final List<String> encryptedFileLines = Files.readAllLines(pathEncryptedText);
    assertEquals(encryptedFileLines.size(), 4);
    assertEquals(encryptedFileLines.get(0), "Lwl");
    assertEquals(encryptedFileLines.get(1), "xl");
    assertEquals(encryptedFileLines.get(2), "vz4l.");
    assertEquals(encryptedFileLines.get(3), "T CD wl4.");
  }

  @Test
  public void testROT11Decryption() throws IOException {
    FileWriter fw1 = new FileWriter(pathEncryptedText.toFile());
    BufferedWriter bw1 = new BufferedWriter(fw1);
    bw1.write("Lwl\n" + "xl\n" + "vz4l.\n" + "T CD wl4.");
    bw1.close();

    Cryptographer.decryptfile(
        pathEncryptedText.toFile().getPath(), pathDecryptedText.toFile().getPath(), new ROT11());
    final List<String> encryptedFileLines = Files.readAllLines(pathDecryptedText);
    assertEquals(encryptedFileLines.size(), 4);
    assertEquals(encryptedFileLines.get(0), "Ala");
    assertEquals(encryptedFileLines.get(1), "ma");
    assertEquals(encryptedFileLines.get(2), "kota.");
    assertEquals(encryptedFileLines.get(3), "I 12 lat.");
  }

  @Test
  public void testPolybiusEncryption() throws IOException {
    Cryptographer.cryptfile(
        pathPlainText.toFile().getPath(), pathEncryptedText.toFile().getPath(), new Polybius());
    final List<String> encryptedFileLines = Files.readAllLines(pathEncryptedText);
    assertEquals(encryptedFileLines.size(), 4);
    assertEquals(encryptedFileLines.get(0), "113111");
    assertEquals(encryptedFileLines.get(1), "3211");
    assertEquals(encryptedFileLines.get(2), "25344411 ");
    assertEquals(encryptedFileLines.get(3), "24    311144 ");
  }

  @Test
  public void testPolybiusDecryption() throws IOException {
    FileWriter fw1 = new FileWriter(pathEncryptedText.toFile());
    BufferedWriter bw1 = new BufferedWriter(fw1);
    bw1.write("113111\n" + "3211\n" + "25344411 \n" + "24    311144 ");
    bw1.close();

    Cryptographer.decryptfile(
        pathEncryptedText.toFile().getPath(), pathDecryptedText.toFile().getPath(), new Polybius());
    final List<String> encryptedFileLines = Files.readAllLines(pathDecryptedText);
    assertEquals(encryptedFileLines.size(), 4);
    assertEquals(encryptedFileLines.get(0), "ala");
    assertEquals(encryptedFileLines.get(1), "ma");
    assertEquals(encryptedFileLines.get(2), "kota");
    assertEquals(encryptedFileLines.get(3), "i lat");
  }
}
