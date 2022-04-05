package pl.edu.uj.restaurant;

public class WaitPerson extends Thread {
  private final Restaurant restaurant;

  public WaitPerson(Restaurant r) {
    restaurant = r;
    start();
  }

  @Override
  public void run() {
    while (true) {
      while (restaurant.order == null)
        synchronized (this) {
          try {
            wait();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      System.out.println("[WaitPerson] WaitPerson got " + restaurant.order);
      restaurant.order = null;
    }
  }
}
