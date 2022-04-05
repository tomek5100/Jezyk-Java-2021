package pl.edu.uj.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ROT11Test {

  @Test
  void test_crypt() {
    String input = "I 12 lat.";
    String output = "T CD wl4.";

    ROT11 ROT11 = new ROT11();

    String encrypted = ROT11.crypt(input);
    assertEquals(output, encrypted);
  }

  @Test
  void test_decrypt() {
    String input = "T CD wl4.";
    String output = "I 12 lat.";

    ROT11 ROT11 = new ROT11();

    String decrypted = ROT11.decrypt(input);
    assertEquals(output, decrypted);
  }
}
