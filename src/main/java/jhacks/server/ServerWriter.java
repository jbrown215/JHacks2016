/*
 * Sends trade messages to Sockets
 */

package jhacks.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.json.simple.JSONObject;

public class ServerWriter {

	public static void writeTrade( List<Socket> sockets , String security, Double price) {
		
	    Socket sahket = sockets.get(0);
	    OutputStream toServer = sahket.getOutputStream();
        DataOutputStream out = new DataOutputStream(toServer);
				
		PrintWriter tradeRequest = new PrintWriter(sahket.getOutputStream(), true);
		tradeRequest.append(Main.getTradeMessage(security,price).toString());
		System.out.println( sahket.getOutputStream() );
	       
	}
		
	public static void readTrade( List<Sockets> sockets, String security, Double price) {
		Socket sahket = sockets.get(0);
		
	}
}
