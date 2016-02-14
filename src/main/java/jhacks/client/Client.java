package jhacks.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public static void main(String args[]) {
    try {
      Socket echoSocket = new Socket("127.0.0.1", 15213);
      ObjectInputStream in = new ObjectInputStream(echoSocket.getInputStream());
      String val = (String) in.readObject();
      System.out.println(val);

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
