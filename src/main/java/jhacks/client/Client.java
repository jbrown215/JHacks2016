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
        JSONObject jsonConnect = new JSONObject("{\"action\" : \"connect\", \"name\" : \"Test Name\"}");
        JSONObject jsonBuy = new JSONObject("{\"action\" : \"buy\", \"security\" : \"GOOG\", \"price\" : 25.00, \"quantity\" : 10}");
        JSONObject jsonSell = new JSONObject("{\"action\" : \"sell\", \"security\" : \"GOOG\", \"price\" : 25.00, \"quantity\" : 10}");
        pw.println(jsonConnect.toString());
        pw.println(jsonBuy.toString());
        pw.println(jsonSell.toString());
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
