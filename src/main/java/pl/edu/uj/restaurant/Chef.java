package pl.edu.uj.restaurant;

public class Chef extends Thread {
  private final Restaurant restaurant;
  private final WaitPerson waitPerson;

  public Chef(Restaurant r, WaitPerson w) {
    restaurant = r;
    waitPerson = w;
    start();
  }

  @Override
  public void run() {
    while (true) {
      if (restaurant.order == null) {
        restaurant.order = new Order();
        System.out.print("[Chef] Order up! ");
        synchronized (waitPerson) {
          waitPerson.notify();
        }
      }
      try {
        sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
