package jhacks.server;

import java.net.Socket;
import java.util.List;
import org.json.simple.JSONObject;

public class ServerWriter {

	static JSONObject writeTrade( List<Socket> sockets , String security, Double price) {
		
		JSONObject output = Main.getTradeMessage(security, price);
		return output;
	}
	
	
}
