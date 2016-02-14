package jhacks.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
  public static int mPort = 15213;

  public static void main(String args[]) {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(mPort);
    } catch (IOException e) {
      System.out.println("Could not open server socket on port ");
      return;
    }

    System.out.println("Listening for incoming commands on port ");

    while (true) {
    try {
        Socket clientSocket = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject("test");
        System.out.println("recieved connection");
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        String value = (String) in.readObject();
        System.out.print(value);
      } catch (IOException | ClassNotFoundException e) {
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
