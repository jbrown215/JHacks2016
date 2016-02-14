package jhacks.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jhacks.utils.Pair;


public class Market {
  // Map from stock name to a pair, consisting of the list of BUY and SELL orders
  // LEFT is buy, RIGHT is sell
  private Map<String, Pair<List<Order>, List<Order>>> marketInfo = new HashMap<>();
  // Name of all the stocks in this game instance
  private Set<String> stockNames = new HashSet<String>();
  
  public Market(Set<String> stockNames) {
    this.stockNames = stockNames;
  }
  
  public void addBuyOrder(String securityName, double price, int quantity, int orderID, String userName) {
    if (marketInfo.get(securityName) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(securityName, new Pair<List<Order>, List<Order>>(bids, asks));
    }
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
    securities.getLeft().add(new Order(securityName, price, quantity, orderID, userName));
  }
  
  public void addSellOrder(String securityName, double price, int quantity, int orderID, String userName) {
    if (marketInfo.get(securityName) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(securityName, new Pair<List<Order>, List<Order>>(bids, asks));
    }
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
    securities.getRight().add(new Order(securityName, price, quantity, orderID, userName));
  }
  
  public void cancelOrder(String securityName, double price, int quantity, int orderID, String userName) {
	Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
	Order temp = new Order(securityName, price, quantity, orderID, userName);
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
  
  public void unPack(){
	  return;
  }
  
  public void Pack(){
	  return;
  }
}
