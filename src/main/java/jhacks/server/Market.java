package jhacks.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jhacks.utils.Pair;

public class Market {
  // Map from stock name to a pair, consisting of the list of BUY and SELL
  // orders
  // LEFT is buy, RIGHT is sell
  private Map<String, Pair<List<Order>, List<Order>>> marketInfo = new HashMap<>();
  // Name of all the stocks in this game instance
<<<<<<< HEAD
  private Map<String, Double> securities;

  
  //ETFs
  ETF JHackIsrael;
  ETF JHackTechnology;
  
  public Market() {
    securities = new HashMap<>();
    
    //Security names
    securities.put("HackingInc", 0.0);
    securities.put("Maryland", 0.0);
    securities.put("Walmart", 0.0);
    securities.put("CyberTech", 0.0);
    securities.put("IsraelTech", 0.0);
    
    ETF JHackIsrael = new ETF();
    ETF JHackTechnology = new ETF();
    
  }
  
  public void addBuyOrder(String securityName, double price, int quantity, String userName) {
    if (marketInfo.get(securityName) == null) {
=======
  private Set<String> stockNames = new HashSet<String>();

  private User client1;
  private User client2;

  public Market(Set<String> stockNames) {
    this.stockNames = stockNames;
  }

  public void addBuyOrder(String name, double price, int quantity) {
    System.out.println("adding buy order: " + name);
    if (marketInfo.get(name) == null) {
>>>>>>> 3620990dd5c5d65c40afe495b4284b573d2e762d
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(securityName, new Pair<List<Order>, List<Order>>(bids, asks));
    }
<<<<<<< HEAD
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
    securities.getLeft().add(new Order(securityName, price, quantity, userName));
  }
  
  public void addSellOrder(String securityName, double price, int quantity, int orderID, String userName) {
    if (marketInfo.get(securityName) == null) {
=======
    String id = UUID.randomUUID().toString();
    Pair<List<Order>, List<Order>> securities = marketInfo.get(name);
    Order order = new Order(name, price, quantity, id);
    securities.getLeft().add(order);
    attemptToMakeTrade(order, true);
  }

  public void addSellOrder(String name, double price, int quantity) {
    if (marketInfo.get(name) == null) {
>>>>>>> 3620990dd5c5d65c40afe495b4284b573d2e762d
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(securityName, new Pair<List<Order>, List<Order>>(bids, asks));
    }
<<<<<<< HEAD
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
    securities.getRight().add(new Order(securityName, price, quantity, userName));
  }
  
  public void cancelOrder(String securityName, double price, int quantity, int orderID, String userName) {
	Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
	Order temp = new Order(securityName, price, quantity, userName);
	if (securities.getRight().contains(temp)) {
		securities.getRight().remove(temp);
		System.out.println("Cancel Success");
	}else if (securities.getLeft().contains(temp)) {
		securities.getLeft().remove(temp);
		System.out.println("Cancel Success");
	}else {
		System.out.println("Could not locate order");
		System.out.println("Cancel Unsuccessful");
	}
  }
  
  public void unPack(ETF etf){
	  return;
  }
  
  public void Pack(ETF etf){
	  return;
=======
    String id = UUID.randomUUID().toString();
    Pair<List<Order>, List<Order>> securities = marketInfo.get(name);
    Order order = new Order(name, price, quantity, id);
    securities.getRight().add(order);
    attemptToMakeTrade(order, false);
  }

  /**
   * This function is not correct yet. It will only fill orders with larger
   * quantities than the bid instead of filling it incrementally.
   * 
   * @param order
   * @param isBuy
   */
  public void attemptToMakeTrade(Order order, boolean isBuy) {
    if (isBuy) {
      for (Order other : marketInfo.get(order.getName()).getRight()) {
        if (other.getQuantity() >= order.getQuantity() && other.getPrice() <= order.getPrice()) {
          // update user holdings
          if (client1.getOrders().contains(order)) {
            client1.getOrders().remove(order);
            client2.getOrders().remove(other);
          } else {
            client2.getOrders().remove(order);
            client1.getOrders().remove(other);
          }

          if (client2.getOrders().contains(order)) {
            client2.getOrders().remove(order);
            client1.getOrders().remove(other);

          } else {
            client1.getOrders().remove(order);
            client2.getOrders().remove(other);
          }
          // update orders
          System.out.println("TODO update order");
        }
        break;
      }
    } else {
      for (Order other : marketInfo.get(order.getName()).getLeft()) {
        if (other.getQuantity() >= order.getQuantity() && other.getPrice() >= order.getPrice()) {
          // update user holdings
          // update orders
          if (client1.getOrders().contains(order)) {
            client1.getOrders().remove(order);
            client2.getOrders().remove(other);
          } else {
            client2.getOrders().remove(order);
            client1.getOrders().remove(other);
          }

          if (client2.getOrders().contains(order)) {
            client2.getOrders().remove(order);
            client1.getOrders().remove(other);

          } else {
            client1.getOrders().remove(order);
            client2.getOrders().remove(other);
          }
        }
        System.out.println("TODO update order");
      }
    }
  }
  
  public void cancelOrder(String id) {
    for (Pair<List<Order>,List<Order>> val: marketInfo.values()) {
    	for(Order buyOrd: val.getLeft()) {
    		if(id.equals(buyOrd)) {
    		}
    	};
    	for(Order sellOrd: val.getRight()){
    		;
    	}
    }
    
>>>>>>> 3620990dd5c5d65c40afe495b4284b573d2e762d
  }
}
