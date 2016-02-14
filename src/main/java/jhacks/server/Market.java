package jhacks.server;

import java.net.Socket;
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

  private Map<String, Double> securities = new HashMap<>();
  private Set<String> stockNames = new HashSet<String>();
  private List<Socket> sockets;

  private User client1;
  private User client2;

  public Market(Set<String> stockNames, List<Socket> sockets) {
    this.stockNames = stockNames;
    this.sockets = sockets;
  }

  public void addBuyOrder(String name, double price, int quantity) {
    if (marketInfo.get(name) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(name, new Pair<List<Order>, List<Order>>(bids, asks));
    }

    String id = UUID.randomUUID().toString();
    Pair<List<Order>, List<Order>> securities = marketInfo.get(name);
    Order order = new Order(name, price, quantity, id);
    securities.getLeft().add(order);
    // attemptToMakeTrade(order, true);

    // #NOTIFY all the users
    ServerWriter.writeTrade(sockets, name, price);
    Map<String, List<Double>> buyList = getBuyList(marketInfo);
//    System.out.println(buyList);
    Map<String, List<Double>> sellList = getSellList(marketInfo);
    ServerWriter.writeState(sockets, buyList, sellList);
    
    for(String key: marketInfo.keySet()) {
    	System.out.println(key + "\t" + marketInfo.get(key).getClass() + marketInfo.size());
    }
  }

  public void addSellOrder(String name, double price, int quantity) {
    if (marketInfo.get(name) == null) {
      ArrayList<Order> bids = new ArrayList<Order>();
      ArrayList<Order> asks = new ArrayList<Order>();
      marketInfo.put(name, new Pair<List<Order>, List<Order>>(bids, asks));
    }

    String id = UUID.randomUUID().toString();
    Pair<List<Order>, List<Order>> securities = marketInfo.get(name);
    Order order = new Order(name, price, quantity, id);
    securities.getRight().add(order);
    // attemptToMakeTrade(order, false);
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
    	  //Update user holdings, user orders, and market orders
    	  if (other.getPrice() <= order.getPrice()) {
        	// For equality
        	if (other.getQuantity() == order.getQuantity()){
        		//User holdings
        		if (client1.getOrders().contains(order)){
        			client1.addHoldings(order.getName(), order.getQuantity());
        			client2.removeHoldings(other.getName(), other.getQuantity());
        		}else if (client2.getOrders().contains(order)){
        			client2.addHoldings(order.getName(), order.getQuantity());
        			client1.removeHoldings(other.getName(), other.getQuantity());
        		}
        		//User orders
        		if (client1.getOrders().contains(order)) {
        			client1.getOrders().remove(order);
        			client2.getOrders().remove(other);
        		} else if (client2.getOrders().contains(order)) {
        			client2.getOrders().remove(order);
        			client1.getOrders().remove(other);
        		}
        		//Remove market orders 
        		marketInfo.remove(order.getName(), order);
        		marketInfo.remove(order.getName(), other);
        		

        	//Update orders incrementally
        	} else if (other.getQuantity() < order.getQuantity()){
        		//User holdings
        		int difference = order.getQuantity() - other.getQuantity();
        		if (client1.getOrders().contains(order)){ 
        			client1.addHoldings(order.getName(), difference);
        			client2.removeHoldings(other.getName(), other.getQuantity());
        		} else if (client2.getOrders().contains(order)){
        			client2.addHoldings(order.getName(), difference);
        			client1.removeHoldings(other.getName(), other.getQuantity());
        		}
        		//User orders
        		if (client1.getOrders().contains(order)){
        			client1.getOrders().add(new Order(order.getName(), order.getPrice(), order.getQuantity(), order.getId()));
        			client2.getOrders().remove(other);
        		}else if (client2.getOrders().contains(order)){
        			client2.getOrders().add(new Order(order.getName(), order.getPrice(), order.getQuantity(), order.getId()));
        			client1.getOrders().remove(other);
        		}
        		//Remove/Change market orders
        		marketInfo.put
        		marketInfo.remove(other);
        		
        	} else if (order.getQuantity() < other.getQuantity()){
        		
        	}
            //Update prices
        	if (other.getPrice() == order.getPrice()) {
        		String name = order.getName();
        		
        	}
          
          System.out.println("TODO update order");
        }
        break;
      }
    } else {
      for (Order other : marketInfo.get(order.getName()).getLeft()) {
        if (other.getPrice() >= order.getPrice()) {
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
    for (Pair<List<Order>, List<Order>> val : marketInfo.values()) {
      for (Order buyOrd : val.getLeft()) {
        if (id.equals(buyOrd)) {
          val.getLeft().remove(id);
        }
      }
      ;
      for (Order sellOrd : val.getRight()) {
        if (id.equals(sellOrd)) {
          val.getRight().remove(id);
        }
      }
    }

  }
  
  public Map<String, List<Double>> getBuyList(Map<String, Pair<List<Order>, List<Order>>> marketInfo) {
	  Map<String, List<Double>> buyList = new HashMap<String,List<Double>>();
	  for(String key: marketInfo.keySet() ){
		  List<Double> prices = new ArrayList<>();
		  for(Order order: marketInfo.get(key).getLeft()) {
		      prices.add( order.getPrice() );
		  }
		  buyList.put(key, prices);
	  }
	  return buyList;
  }
  
  public Map<String, List<Double>> getSellList(Map<String, Pair<List<Order>,List<Order>>> marketInfo) {
	  Map<String, List<Double>> sellList = new HashMap<String,List<Double>>();
	  for(String key: marketInfo.keySet()) {
		  List<Double> prices = new ArrayList<>();
		  for(Order order: marketInfo.get(key).getRight()) {
			  prices.add( order.getPrice() );
		  }
		  sellList.put(key,prices);
	  }
	  return sellList;
  }

  }
  
  

