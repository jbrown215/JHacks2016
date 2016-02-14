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

	public static Map sendStateMessage(Map<String, Double> buyMap) {
		
		// Get top 5 sells
		ArrayList<String> top5sells = new ArrayList<>();
		List<Double> vals = new ArrayList<>(buyMap.values());
		Collections.sort(vals, Collections.reverseOrder());
		for(int i=0; i<5; i++){
			top5sells.add(vals.get(i).toString());
		}
		System.out.println(top5sells);
		
		// TYPE??
		JSONObject state = new JSONObject();
		Map marketInfo = new HashMap();

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
