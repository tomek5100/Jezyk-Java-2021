package pl.edu.uj.crypt;

public interface Algorithm {

  String crypt(String inputWord);

  String decrypt(String inputWord);
}
