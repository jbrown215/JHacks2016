package jhacks.server;

public class Order {
  private final String name;
  private final double price;
  private final int quantity;
  
  public Order(String name, double price, int quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }
  
  public String getName() {
    return this.name;
  }
  
  public double getPrice() {
    return this.price;
  }
  
  public int getQuantity() {
    return this.quantity;
  }
}
