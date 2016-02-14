package jhacks.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class MasterServer {
  public static int mPort = 15213;
  public static Queue<Socket> clientConnections = new LinkedList<Socket>();

  public static void main(String args[]) {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(mPort);
    } catch (IOException e) {
      System.out.println("Could not open server socket on port ");
      return;
    }

    System.out.println("Listening for incoming commands on port ");

    int counter = 0;
    while (true) {
      try {
        Socket clientSocket = serverSocket.accept();
        counter++;
        if (counter % 2 != 0) {
          clientConnections.offer(clientSocket);
        }
        if (clientConnections.size() >= 2 && counter % 2 != 0) {
          System.out.println("starting game...");
          Socket client1 = clientConnections.poll();
          Socket client2 = clientConnections.poll();
          GameRunnable game = new GameRunnable(client1, client2);
          Thread t = new Thread(game);
          t.start();
        }
        counter = counter % 2;
      } catch (IOException e) {
        System.out.println("Error while listening for incoming connections.");
        break;
      }
    }

    try {
      serverSocket.close();
    } catch (IOException e) {
      System.out.println("Caught error while shutting down");
    }
  }
}
