package pl.edu.uj.crypt;

public class Polybius implements Algorithm {

  static final Character[][] board = {
    {'a', 'b', 'c', 'd', 'e', null},
    {'f', 'g', 'h', 'i', 'k', 'j'},
    {'l', 'm', 'n', 'o', 'p', null},
    {'q', 'r', 's', 't', 'u', null},
    {'v', 'w', 'x', 'y', 'z', null},
  };

  @Override
  public String crypt(String inputWord) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < inputWord.length(); i++) {
      char c = inputWord.charAt(i);
      final int coordinates = findInBoard(c);
      if (coordinates < 0) {
        result.append(' ');
      } else {
        result.append(Integer.valueOf(coordinates));
      }
    }
    return result.toString();
  }

  @Override
  public String decrypt(String inputWord) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < inputWord.length(); i += 2) {
      String cipher = inputWord.substring(i, i + 2);
      int coordinates = Integer.parseInt(cipher);
      final char decrypted = getFromBoard(coordinates);
      result.append(decrypted);
    }
    return result.toString();
  }

  private static char getFromBoard(int coordinates) {
    int coordinatesShifted = coordinates - 11;
    int xCoordinate = coordinatesShifted % 10;
    int yCoordinate = (coordinatesShifted - xCoordinate) / 10;
    if (coordinatesShifted < 0 || xCoordinate > 6 || yCoordinate > 5) {
      throw new IllegalArgumentException("! got coordinates= " + coordinates);
    }
    final Character character = board[yCoordinate][xCoordinate];
    if (character == null) {
      throw new IllegalArgumentException("! got null character for coordinates= " + coordinates);
    }
    return character;
  }

  private static int findInBoard(char character) {
    final char lowerCased = Character.toLowerCase(character);
    for (int yCoordinate = 0; yCoordinate < 5; yCoordinate++) {
      for (int xCoordinate = 0; xCoordinate < 6; xCoordinate++) {
        final Character fromBoard = board[yCoordinate][xCoordinate];
        if (fromBoard != null && lowerCased == fromBoard) {
          return yCoordinate * 10 + xCoordinate + 11;
        }
      }
    }
    return -1;
  }
}
