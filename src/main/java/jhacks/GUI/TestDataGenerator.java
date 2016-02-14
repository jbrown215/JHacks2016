package jhacks.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javax.swing.Timer;

import org.json.JSONException;
import org.json.JSONObject;

import jhacks.client.Client;

public class TestDataGenerator implements Runnable {

  private Map<String, Queue<Map<String, Double>>> map;
  private static final Random random = new Random();

  public TestDataGenerator(Map<String, Queue<Map<String, Double>>> map) {
    this.map = map;
  }

  public JSONObject stringToJSON(String json) {
    try {
      JSONObject jsonObj = new JSONObject(json);
      return jsonObj;
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void run() {
    try {
      Socket serverSocket = new Socket("127.0.0.1", 15213);
      PrintWriter pw = new PrintWriter(serverSocket.getOutputStream(), true);
      BufferedReader socketReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
      try {
        JSONObject jsonConnect = new JSONObject("{\"action\" : \"connect\", \"name\" : \"Test Name\"}");
        JSONObject jsonBuy = new JSONObject(
            "{\"action\" : \"buy\", \"security\" : \"GOOG\", \"price\" : 25.00, \"quantity\" : 10}");
        JSONObject jsonSell = new JSONObject(
            "{\"action\" : \"sell\", \"security\" : \"GOOG\", \"price\" : 25.00, \"quantity\" : 10}");
        pw.println(jsonConnect.toString());
        pw.println(jsonBuy.toString());
        pw.println(jsonSell.toString());
        JSONObject jsonCancel = new JSONObject("{\"action\" : \"cancel\", \"id\": \"1234\"}");
        pw.println(jsonCancel.toString());

      } catch (JSONException e) {
        e.printStackTrace();
      }

      RandomTrade trader = new RandomTrade(serverSocket);
      Thread t = new Thread(trader);
      t.start();
      Timer timer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          Map<String, Double> mapVal = new HashMap<>();
          String val;
          try {
            val = socketReader.readLine();
            JSONObject obj = stringToJSON(val);
            if (obj.getString("subject").equals("trade")) {
              mapVal.put("price", obj.getDouble("price"));
              map.get(obj.get("security")).offer(mapVal);
            }
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      });
      timer.setRepeats(true);
      timer.start(); // Go go go!
    } catch (

    UnknownHostException e)

    {
      e.printStackTrace();
    } catch (

    IOException e)

    {
      e.printStackTrace();
    }
  }

}
