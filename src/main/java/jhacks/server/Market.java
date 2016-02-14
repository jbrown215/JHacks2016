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
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(securityName, new Pair<List<Order>, List<Order>>(bids, asks));
    }
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(securityName);
    securities.getLeft().add(new Order(securityName, price, quantity, userName));
  }
  
  public void addSellOrder(String securityName, double price, int quantity, int orderID, String userName) {
    if (marketInfo.get(securityName) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(securityName, new Pair<List<Order>, List<Order>>(bids, asks));
    }
    
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
  }
}
