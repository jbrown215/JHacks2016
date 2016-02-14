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
  private final User user1;
  private final User user2;

  public GameRunnable(Socket client1, Socket client2) {
    this.client1 = client1;
    this.client2 = client2;
    Set<String> stocks = new HashSet<String>();
    List<Socket> sockets = new ArrayList<Socket>();
    sockets.add(client1);
    sockets.add(client2);
    Set<String> securities = new HashSet<String>();
    securities.add("GOOG");
    securities.add("MOOG");
    securities.add("FOOD");
    securities.add("THROOG");
    securities.add("BOOG");
    user1 = new User(securities, null);
    user2 = new User(securities, null);
    this.market = new Market(sockets, user1, user2);
    user1.marketplace = market;
    user2.marketplace = market;
  }

  @Override
  public void run() {
    System.out.println("Starting server readers...");
    ServerReader client1Reader = new ServerReader(client1, this, user1);
    ServerReader client2Reader = new ServerReader(client2, this, user2);
    
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
