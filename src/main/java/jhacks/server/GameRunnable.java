package jhacks.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameRunnable implements Runnable {

  private final Socket client1;
  private final Socket client2;
  private final List<String> teams = new ArrayList<String>();
  private final Market market;

  public GameRunnable(Socket client1, Socket client2) {
    this.client1 = client1;
    this.client2 = client2;
    Set<String> stocks = new HashSet<String>();
    stocks.add("GOOG");
    stocks.add("FB");
    List<Socket> sockets = new ArrayList<Socket>();
    sockets.add(client1);
    sockets.add(client2);
    this.market = new Market(stocks, sockets);
  }

  @Override
  public void run() {
    System.out.println("Starting server readers...");
    ServerReader client1Reader = new ServerReader(client1, this);
    ServerReader client2Reader = new ServerReader(client2, this);
    
    Thread client1Thread = new Thread(client1Reader);
    Thread client2Thread = new Thread(client2Reader);
    
    client1Thread.start();
    client2Thread.start();
    
  }
  
  
  public List<String> getTeamArray() {
    return this.teams;
  }
  
  public Market getMarket() {
    return this.market;
  }

}
