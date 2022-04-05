package pl.edu.uj.pesel;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeselTest {

  @Test
  public void testPeselCompare() {
    assertTrue(Pesel.compare(new Pesel("55030101230"), new Pesel("55030101230")));
    assertFalse(Pesel.compare(new Pesel("55030101193"), new Pesel("55030101194")));
  }

  @Test
  public void testPeselValid() {
    assertTrue(Pesel.check(new Pesel("55030101230")));
    assertTrue(Pesel.check(new Pesel("55030101193")));
  }

  @Test
  public void testPeselInvalid() {
    assertFalse(Pesel.check(new Pesel("550301011931")));
    assertFalse(Pesel.check(new Pesel("55030101194")));
  }

  @Test(expected = NullPointerException.class)
  public void testPeselNull() {
    Pesel.check(new Pesel(null));
  }
}