package pl.edu.uj.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolybiusTest {

  @Test
  void test_crypt() {
    String input = "kota";
    String output = "25344411";

    Polybius polybius = new Polybius();

    String encrypted = polybius.crypt(input);
    assertEquals(output, encrypted);
  }

  @Test
  void test_decrypt() {
    String input = "25344411";
    String output = "kota";

    Polybius polybius = new Polybius();

    String decrypted = polybius.decrypt(input);
    assertEquals(output, decrypted);
  }
}
