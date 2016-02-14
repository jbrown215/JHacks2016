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
  public static int mPort = 22;
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
        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
        pw.println(
            "{\"subject\" : \"state\", \"marketInfo\" : [{\"name\",[1,2,3,4,5],[5,4,3,2,1],12},{\"name2\",[2,3,4,5,6],[6,5,4,3,2],3}]}");
        System.out.println("test");
        /*
         * if (counter % 2 != 0) { clientConnections.offer(clientSocket); } if
         * (clientConnections.size() >= 2 && counter % 2 != 0) {
         * System.out.println("starting game..."); Socket client1 =
         * clientConnections.poll(); Socket client2 = clientConnections.poll();
         * ObjectOutputStream out1 = new
         * ObjectOutputStream(client1.getOutputStream()); ObjectOutputStream
         * out2 = new ObjectOutputStream(client2.getOutputStream()); //
         * GameRunnable game = new GameRunnable(client1, client2); // Thread t =
         * new Thread(game); // t.start(); while (true) { out1.writeObject(
         * "{\"subject\" : \"state\", \"marketInfo\" : [{\"name\",[1,2,3,4,5],[5,4,3,2,1],12},{\"name2\",[2,3,4,5,6],[6,5,4,3,2],3}]}"
         * ); out2.writeObject(
         * "{\"subject\" : \"state\", \"marketInfo\" : [{\"name\",[1,2,3,4,5],[5,4,3,2,1],12},{\"name2\",[2,3,4,5,6],[6,5,4,3,2],3}]}"
         * ); } } counter = counter % 2;
         */
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
