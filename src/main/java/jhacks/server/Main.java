/*
 * Sends messages to server.
 * 
 * Receives 2 maps as input:
 * 		Stocks: Buy orders
 * 		Stocks: Sell orders
 * 
 * Outputs: 
 * 		  {subject : state, marketInfo : [{stock, [top 5 bids], [top 5 asks]}…], score}
	      {subject : trade, security: name, price : $$}
 */


package jhacks.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;


public class Main {

	public static Map sendStateMessage(Map<String, Double[]> buyMap) {
		
		// Get top 5 buy orders/bids
		ArrayList<String> top5bids = new ArrayList<>();
//		top5
//		List<Double> vals = new ArrayList<>(buyMap.values());
//		Collections.sort(vals, Collections.reverseOrder());
		
		System.out.println(top5bids);
		
	   

		
		// TYPE??
		JSONObject state = new JSONObject();
		ArrayList<Pair> marketInfo = new ArrayList<>();
		HashMap info = new HashMap();
		info.put("name", top5bids.get(0));
		info.put("bids", buyMap.get( top5bids.get(0) ));
		
	    state.put("subject", "state");
	    state.put("marketInfo", "PASS____");

	    System.out.print(state);
		
	    JSONObject trade = new JSONObject();
	    
		return state;
	    
	    /*
	      STATE
	      {subject : state, marketInfo : [{sname : tock, bids : [top 5 bids], asks : [top 5 asks]}…], score}
	      {subject : trade, security: name, price : $$}
	      [ {name: stock, [bids], [asks] }
	      */
	}
	
	public static JSONObject sendTradeMessage(String security, Double price) {
		JSONObject trade = new JSONObject();
		trade.put("subject", "trade");
		trade.put("security", security);
		trade.put("price", price);
		
		return trade;
	}
}

	
	
/*

SERVER MESSAGES:

state
trade
x confirmation


STATE
{subject : state, marketInfo : [{stock, [top 5 bids], [top 5 asks]}…], score}
{subject : trade, security: name, price : $$}


TEAMS:

GUI (server side data + client side setup)
Driving Agents/Testing agents
Server



Requirements from user: 
runnable bot
./run.sh script 
adi
words
Ayelet
Jon
*/
