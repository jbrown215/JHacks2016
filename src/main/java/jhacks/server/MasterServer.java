package jhacks.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        System.out.println("received connection");
        clientConnections.offer(clientSocket);

        while (clientConnections.size() >= 2) {
          Socket client1 = clientConnections.poll();
          Socket client2 = clientConnections.poll();
          GameRunnable game = new GameRunnable(client1, client2);
          Thread t = new Thread(game);
          t.start();
        }
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
