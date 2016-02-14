package jhacks.server;

public class Order {
  private final String securityName;
  private final double price;
  private final int quantity;
  private final String userName;
  
  public Order(String securityName, double price, int quantity, String userName) {
    this.securityName = securityName;
    this.price = price;
    this.quantity = quantity;
    this.userName = userName;
  }
  
  public String getName() {
    return this.securityName;
  }
  
  public double getPrice() {
    return this.price;
  }
  
  public int getQuantity() {
    return this.quantity;
  }
  
  public String getUserName(){
	return this.userName;
  }
}
