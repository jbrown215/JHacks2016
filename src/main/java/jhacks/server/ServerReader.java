package jhacks.server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerReader implements Runnable {

  private Socket client;
  private GameRunnable game;

  public ServerReader(Socket client, GameRunnable game) {
    this.client = client;
    this.game = game;
  }

  public void connect(String name) {
    synchronized (game.getTeamArray()) {
      game.getTeamArray().add(name);
      System.out.println("Name"+name);
    }
  }

  public void buy(String name, double price, int quantity) {
    synchronized (game.getMarket()) {
      game.getMarket().addBuyOrder(name, price, quantity);
    }
  }

  public void sell(String name, double price, int quantity) {
    synchronized (game.getMarket()) {
      game.getMarket().addSellOrder(name, price, quantity);
    }
  }

  public void cancel(String id) {
    synchronized (game.getMarket()) {
      game.getMarket().cancelOrder(id);
    }
  }

  @Override
  public void run() {

    BufferedReader socketReader;
    try {
      socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      while (true) {
        String str = socketReader.readLine();
        JSONObject json = new JSONObject(str);
        if (json.getString("action").equals("connect")) {
          this.connect(json.getString("name"));
        } else if (json.getString("action").equals("buy")) {
          this.buy(json.getString("security"), json.getDouble("price"), json.getInt("quantity"));
        } else if (json.getString("action").equals("sell")) {
          this.sell(json.getString("security"), json.getDouble("price"), json.getInt("quantity"));
        } else if (json.getString("action").equals("cancel")) {
          System.out.println("ID"+json.get("id"));
          this.cancel(json.getString("id"));
        }
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
  }

}
