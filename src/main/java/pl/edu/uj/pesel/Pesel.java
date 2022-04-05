package pl.edu.uj.pesel;

import java.util.List;
import java.util.Objects;

public class Pesel {

  private static final List<Integer> WEIGHTS = List.of(1, 3, 7, 9);
  private static final Integer PESEL_LEN = 11;

  private final String pesel;

  public Pesel(String pesel) {
    this.pesel = pesel;
  }

  public static boolean compare(Pesel a, Pesel b) {
    return a.equals(b);
  }

  static boolean check(Pesel pesel) {
    if (pesel.pesel.length() != PESEL_LEN) {
      return false;
    }

    int controlSum = 0;

    for (int i = 0; i < pesel.pesel.length() - 1; i++) {
      char c = pesel.pesel.charAt(i);
      int peselNum = Character.getNumericValue(c);
      int wIndex = i % 4;
      controlSum += peselNum * WEIGHTS.get(wIndex);
    }

    final int lastNumber = Character.getNumericValue(pesel.pesel.charAt(PESEL_LEN - 1));
    int controlSumValidation = controlSum + lastNumber;
    controlSumValidation = controlSumValidation % 10;

    if (controlSum % 10 == 0) {
      controlSum = 0;
    } else {
      controlSum = 10 - controlSum % 10;
    }

    return controlSum == lastNumber && controlSumValidation == 0;
  }

  public String getPesel() {
    return pesel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pesel pesel1 = (Pesel) o;
    return Objects.equals(pesel, pesel1.pesel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pesel);
  }
}
