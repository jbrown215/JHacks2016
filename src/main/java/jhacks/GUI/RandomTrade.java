package jhacks.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.Timer;

import org.json.JSONException;
import org.json.JSONObject;

public class RandomTrade implements Runnable {

  private Socket socket;
  private Random rand = new Random();

  public RandomTrade(Socket socket) {
    this.socket = socket;
  }

  public String randomName() {
    String[] names = new String[5];
    names[0] = "MOOG";
    names[1] = "GOOG";
    names[2] = "FOOD";
    names[3] = "THROOG";
    names[4] = "BOOG";
    return names[rand.nextInt(5)];

  }

  public int randomPrice() {
    return rand.nextInt(50) + 25;
  }

  public String createAsk() {
    return "{\"action\" : \"sell\", \"security\" :\"" + randomName() + "\", \"price\":"
        + Integer.toString(randomPrice()) + ", \"quantity\" : 10, \"orderNumber\" : 10}";
  }

  public String createBid() {
    return "{\"action\" : \"buy\", \"security\" :\"" + randomName() + "\", \"price\":" + Integer.toString(randomPrice())
        + ", \"quantity\" : 10, \"orderNumber\" : 10}";
  }

  @Override
  public void run() {
    try {
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      int i = 0;
      while (true) {
        if (i % 1000 != 0) {
          i++;
          continue;
        }
        i++;
        String ask = createAsk();
        String bid = createBid();
        System.out.println(ask);
        System.out.println(bid);
        writer.println(ask);
        writer.println(bid);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
