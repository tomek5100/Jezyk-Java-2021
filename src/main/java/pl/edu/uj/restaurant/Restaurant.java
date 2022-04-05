package pl.edu.uj.restaurant;

public class Restaurant {
  Order order;

  public static void main(String[] args) {
    Restaurant restaurant = new Restaurant();
    WaitPerson waitPerson = new WaitPerson(restaurant);
    Chef chef = new Chef(restaurant, waitPerson);
  }
}
