package pl.edu.uj.crypt;

public class ROT11 implements Algorithm {

  private static final String ALPHABET =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String ALPHABET_ROTED =
      "LMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJK";

  @Override
  public String crypt(String inputWord) {
    return cryptOrDecrypt(inputWord, true);
  }

  @Override
  public String decrypt(String inputWord) {
    return cryptOrDecrypt(inputWord, false);
  }

  private String cryptOrDecrypt(String inputWord, boolean crypt) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < inputWord.length(); i++) {
      char c = inputWord.charAt(i);
      int index = crypt ? ALPHABET.indexOf(c) : ALPHABET_ROTED.indexOf(c);
      if (index < 0) {
        result.append(c);
      } else {
        result.append(crypt ? ALPHABET_ROTED.charAt(index) : ALPHABET.charAt(index));
      }
    }
    return result.toString();
  }
}
