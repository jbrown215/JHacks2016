package jhacks.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
  private Map<String, Integer> holdings;
  private Set<Order> orders;
  private String name;
  private int score;
  private Market marketplace;
  
  public User(Set<String> stockNames, Market marketplace) {
    holdings = new HashMap<String, Integer>();
    orders = new HashSet<Order>();
    for (String stock : stockNames) {
      holdings.put(stock, Integer.valueOf(0));
    }
    this.marketplace = marketplace;
  }
  
  public int getScore() {
    
  }
  
  public String getName() {
    return name;
  }
  
  public void addHoldings(String stockName, int num) {
    Integer numHolding = holdings.get(stockName);
    holdings.put(stockName, num + numHolding);
  }

  public void removeHoldings(String stockName, int num) {
    Integer numHolding = holdings.get(stockName);
    holdings.put(stockName, numHolding - num);
  }
  
  public Set<Order> getOrders() {
    return this.orders;
  }
}
