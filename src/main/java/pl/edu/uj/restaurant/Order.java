package pl.edu.uj.restaurant;

public class Order {
  private static int i = 0;
  private final int count = i++;

  public Order() {
    if (count == 10) {
      System.out.println("[Order] Out of food, closing");
      System.exit(0);
    }
  }

  @Override
  public String toString() {
    return "Order " + count;
  }
}
