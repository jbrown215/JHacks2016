package jhacks.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

public class Client {
  public static void main(String args[]) {
    try {
      Socket serverSocket = new Socket("127.0.0.1", 15213);
      PrintWriter pw = new PrintWriter(serverSocket.getOutputStream(), true);
      try {
        JSONObject json = new JSONObject("{\"action\" : \"connect\", \"name\" : \"Test Name\"}");
        pw.println(json.toString());
      } catch (JSONException e) {
        e.printStackTrace();
      }
      while(true) {}
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
