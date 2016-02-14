package jhacks.server;

import java.net.Socket;

public class GameRunnable implements Runnable {

  private final Socket client1;
  private final Socket client2;

  public GameRunnable(Socket client1, Socket client2) {
    this.client1 = client1;
    this.client2 = client2;
  }

  @Override
  public void run() {
    System.out.println("In a new game with socket");
  }

}
