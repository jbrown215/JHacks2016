package jhacks.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
      BufferedReader socketReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
      try {
        JSONObject jsonConnect = new JSONObject("{\"action\" : \"connect\", \"name\" : \"Test Name\"}");
        JSONObject jsonBuy = new JSONObject("{\"action\" : \"buy\", \"security\" : \"GOOG\", \"price\" : 25.00, \"quantity\" : 10}");
        JSONObject jsonSell = new JSONObject("{\"action\" : \"sell\", \"security\" : \"GOOG\", \"price\" : 25.00, \"quantity\" : 10}");
        pw.println(jsonConnect.toString());
        pw.println(jsonBuy.toString());
        pw.println(jsonSell.toString());
        JSONObject jsonCancel = new JSONObject("{\"action\" : \"cancel\", \"id\": \"1234\"}");
        pw.println(jsonCancel.toString());
        
      } catch (JSONException e) {
        e.printStackTrace();
      }
      while(true) {
        String val = socketReader.readLine();
//        System.out.println(val);
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
