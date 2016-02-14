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
  private List<Socket> sockets;

  private User client1;
  private User client2;

  public Market(List<Socket> sockets) {
    this.initializeMap();
    this.sockets = sockets;
  }

  public void initializeMap() {
    securities.put("GOOG", 0.0);
    securities.put("MOOG", 0.0);
    securities.put("FOOD", 0.0);
    securities.put("THROOG", 0.0);
    securities.put("BOOG", 0.0);
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
    Map<String, List<Double>> buyList = getBuyList(marketInfo);
    // System.out.println(buyList);
    Map<String, List<Double>> sellList = getSellList(marketInfo);
    ServerWriter.writeState(sockets, buyList, sellList);

    for (String key : marketInfo.keySet()) {
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

  public void attemptToMakeTrade(Order order, boolean isBuy) {
    if (isBuy) {
      for (Order other : marketInfo.get(order.getName()).getRight()) {
        // Update user holdings, user orders, and market orders
        if (other.getPrice() <= order.getPrice()) {
          ServerWriter.writeTrade(sockets, order.getName(), other.getPrice());
          // For equality
          if (other.getQuantity() == order.getQuantity()) {
            // User holdings
            if (client1.getOrders().contains(order)) {
              client1.addHoldings(order.getName(), order.getQuantity());
              client2.removeHoldings(other.getName(), other.getQuantity());
            } else if (client2.getOrders().contains(order)) {
              client2.addHoldings(order.getName(), order.getQuantity());
              client1.removeHoldings(other.getName(), other.getQuantity());
            }
            // User orders
            if (client1.getOrders().contains(order)) {
              client1.getOrders().remove(order);
              client2.getOrders().remove(other);
            } else if (client2.getOrders().contains(order)) {
              client2.getOrders().remove(order);
              client1.getOrders().remove(other);
            }
            // Remove market orders
            marketInfo.remove(order.getName(), order);
            marketInfo.remove(order.getName(), other);

            // For inequality
          } else if (other.getQuantity() < order.getQuantity()) {
            // User holdings
            int difference = order.getQuantity() - other.getQuantity();
            if (client1.getOrders().contains(order)) {
              client1.addHoldings(order.getName(), other.getQuantity());
              client2.removeHoldings(other.getName(), other.getQuantity());
            } else if (client2.getOrders().contains(order)) {
              client2.addHoldings(order.getName(), other.getQuantity());
              client1.removeHoldings(other.getName(), other.getQuantity());
            }
            // User orders
            if (client1.getOrders().contains(order)) {
              client1.getOrders().add(new Order(order.getName(), order.getPrice(), difference, order.getId()));
              client1.getOrders().remove(order);
              client2.getOrders().remove(other);
            } else if (client2.getOrders().contains(order)) {
              client2.getOrders().add(new Order(order.getName(), order.getPrice(), difference, order.getId()));
              client2.getOrders().remove(order);
              client1.getOrders().remove(other);
            }
            // Remove/Change market orders
            marketInfo.remove(other);
            for (Order find : marketInfo.get(order.getName()).getRight()) {
              if (find == order) {
                find.changeQuantity(difference);
              }
            }
            // Other inequality
          } else if (order.getQuantity() < other.getQuantity()) {
            // User holdings
            int difference = other.getQuantity() - order.getQuantity();
            if (client1.getOrders().contains(other)) {
              client1.addHoldings(other.getName(), order.getQuantity());
              client2.removeHoldings(order.getName(), order.getQuantity());
            } else if (client2.getOrders().contains(other)) {
              client2.addHoldings(other.getName(), order.getQuantity());
              client1.removeHoldings(order.getName(), order.getQuantity());
            }
            // User orders
            if (client1.getOrders().contains(other)) {
              client1.getOrders().add(new Order(other.getName(), other.getPrice(), difference, other.getId()));
              client1.getOrders().remove(other);
              client2.getOrders().remove(order);
            } else if (client2.getOrders().contains(order)) {
              client2.getOrders().add(new Order(other.getName(), other.getPrice(), difference, other.getId()));
              client2.getOrders().remove(other);
              client1.getOrders().remove(order);
            }
            // Remove/Change market orders
            marketInfo.remove(order);
            for (Order find : marketInfo.get(other.getName()).getRight()) {
              if (find == other) {
                find.changeQuantity(difference);
              }
            }

          }
          // Update prices
          if (other.getPrice() == order.getPrice()) {
            String name = order.getName();
            securities.replace(name, order.getPrice());
          } else if (order.getPrice() < other.getPrice()) {
            String name = order.getName();
            securities.replace(name, other.getPrice());
          }
        }
      }
    } else {
      for (Order other : marketInfo.get(order.getName()).getLeft()) {
        if (other.getPrice() >= order.getPrice()) {
          ServerWriter.writeTrade(sockets, order.getName(), other.getPrice());
          // For equality in quantity
          if (other.getQuantity() == order.getQuantity()) {
            // User holdings
            if (client1.getOrders().contains(order)) {
              client2.addHoldings(order.getName(), order.getQuantity());
              client1.removeHoldings(other.getName(), other.getQuantity());
            } else if (client2.getOrders().contains(order)) {
              client1.addHoldings(order.getName(), order.getQuantity());
              client2.removeHoldings(other.getName(), other.getQuantity());
            }
            // User orders
            if (client1.getOrders().contains(order)) {
              client1.getOrders().remove(order);
              client2.getOrders().remove(other);
            } else if (client2.getOrders().contains(order)) {
              client2.getOrders().remove(order);
              client1.getOrders().remove(other);
            }
            // Remove market orders
            marketInfo.remove(order.getName(), order);
            marketInfo.remove(order.getName(), other);

            // For inequality in quantity
          } else if (other.getQuantity() > order.getQuantity()) {
            // User holdings
            int difference = order.getQuantity() - other.getQuantity();
            if (client1.getOrders().contains(order)) {
              client2.addHoldings(other.getName(), order.getQuantity());
              client1.removeHoldings(order.getName(), order.getQuantity());
            } else if (client2.getOrders().contains(order)) {
              client1.addHoldings(order.getName(), order.getQuantity());
              client2.removeHoldings(other.getName(), order.getQuantity());
            }
            // User orders
            if (client1.getOrders().contains(order)) {
              client2.getOrders().add(new Order(other.getName(), other.getPrice(), difference, other.getId()));
              client2.getOrders().remove(other);
              client1.getOrders().remove(order);
            } else if (client2.getOrders().contains(order)) {
              client1.getOrders().add(new Order(other.getName(), other.getPrice(), difference, other.getId()));
              client1.getOrders().remove(other);
              client2.getOrders().remove(order);
            }
            // Remove/Change market orders
            marketInfo.remove(order);
            for (Order find : marketInfo.get(other.getName()).getRight()) {
              if (find == other) {
                find.changeQuantity(difference);
              }
            }
            // Other inequality in quantity
          } else if (order.getQuantity() > other.getQuantity()) {
            // User holdings
            int difference = order.getQuantity() - other.getQuantity();
            if (client1.getOrders().contains(order)) {
              client2.addHoldings(other.getName(), other.getQuantity());
              client1.removeHoldings(order.getName(), other.getQuantity());
            } else if (client2.getOrders().contains(order)) {
              client1.addHoldings(other.getName(), other.getQuantity());
              client2.removeHoldings(order.getName(), other.getQuantity());
            }
            // User orders
            if (client1.getOrders().contains(order)) {
              client1.getOrders().add(new Order(order.getName(), order.getPrice(), difference, order.getId()));
              client1.getOrders().remove(order);
              client2.getOrders().remove(other);
            } else if (client2.getOrders().contains(order)) {
              client2.getOrders().add(new Order(order.getName(), order.getPrice(), difference, order.getId()));
              client2.getOrders().remove(order);
              client1.getOrders().remove(other);
            }
            // Remove/Change market orders
            marketInfo.remove(order);
            for (Order find : marketInfo.get(other.getName()).getRight()) {
              if (find == other) {
                find.changeQuantity(difference);
              }
            }

          }
          // Update prices
          if (other.getPrice() == order.getPrice()) {
            String name = order.getName();
            securities.replace(name, order.getPrice());
          } else if (other.getPrice() > order.getPrice()) {
            String name = order.getName();
            securities.replace(name, other.getPrice());
          }
        }
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
    Map<String, List<Double>> buyList = new HashMap<String, List<Double>>();
    for (String key : marketInfo.keySet()) {
      List<Double> prices = new ArrayList<>();
      for (Order order : marketInfo.get(key).getLeft()) {
        prices.add(order.getPrice());
      }
      buyList.put(key, prices);
    }
    return buyList;
  }

  public Map<String, List<Double>> getSellList(Map<String, Pair<List<Order>, List<Order>>> marketInfo) {
    Map<String, List<Double>> sellList = new HashMap<String, List<Double>>();
    for (String key : marketInfo.keySet()) {
      List<Double> prices = new ArrayList<>();
      for (Order order : marketInfo.get(key).getRight()) {
        prices.add(order.getPrice());
      }
      sellList.put(key, prices);
    }
    return sellList;
  }

}
