package jhacks.server;

import java.net.Socket;

public class ServerReader implements Runnable {
  
  private Socket client;

  public ServerReader(Socket client) {
    this.client = client;
  }
  
  @Override
  public void run() {
  }

}
