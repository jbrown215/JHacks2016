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
  
  public void addBuyOrder(String name, double price, int quantity) {
    System.out.println("adding buy order: " + name);
    if (marketInfo.get(name) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(name, new Pair<List<Order>, List<Order>>(bids, asks));
    }
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(name);
    securities.getLeft().add(new Order(name, price, quantity));
  }
  
  public void addSellOrder(String name, double price, int quantity) {
    if (marketInfo.get(name) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(name, new Pair<List<Order>, List<Order>>(bids, asks));
    }
    
    Pair<List<Order>, List<Order>> securities = marketInfo.get(name);
    securities.getRight().add(new Order(name, price, quantity));
  }
}
