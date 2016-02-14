package jhacks.server;

public class Order {
  private final String securityName;
  private final double price;
  private final int quantity;
  private final int orderID;
  private final String userName;
  
  public Order(String securityName, double price, int quantity, int orderID, String userName) {
    this.securityName = securityName;
    this.price = price;
    this.quantity = quantity;
    this.orderID = orderID;
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
  
  public int getOrderID() {
	return this.orderID;
  }
  
  public String getUserName(){
	return this.userName;
  }
}
