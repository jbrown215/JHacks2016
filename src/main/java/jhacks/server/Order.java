package jhacks.server;

public class Order {
  private final String name;
  private final double price;
  private final int quantity;
  private final String id;
  
  public Order(String name, double price, int quantity, String id) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.id = id;
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
  
  public String getId() {
	return this.id;
  }
}
