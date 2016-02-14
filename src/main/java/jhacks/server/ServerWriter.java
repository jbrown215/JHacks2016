/*
 * Sends trade messages to Sockets
 */

package jhacks.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public class ServerWriter {

  public static void writeTrade(List<Socket> sockets, String security, Double price) {

    for (Socket sahket : sockets) {
      OutputStream toServer;
      try {
        toServer = sahket.getOutputStream();

        PrintWriter tradeRequest = new PrintWriter(sahket.getOutputStream(), true);
        tradeRequest.println(Main.getTradeMessage(security, price).toString());
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }
  
  public static void writeState(List<Socket> sockets, Map<String,List<Double>> buyList, 
		  Map<String,List<Double>> sellList) {
	  for (Socket saaket: sockets) {
		  OutputStream toServer;
		  try {
			  toServer = saaket.getOutputStream();
			  PrintWriter stateRequest = new PrintWriter(saaket.getOutputStream(), true);
			  stateRequest.println(Main.getStateMessage(buyList, sellList).toString());
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
  }

}
