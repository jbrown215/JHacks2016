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

	public static Map sendStateMessage(Map<String,List<Double>> buyMap, 
			Map<String,List<Double>> sellMap) {
		
		// Iterate through keys, take 5 best bids
		for(String key: buyMap.keySet()){
			List<Double> vals = buyMap.get(key);
			Collections.sort(buyMap.get(key), Collections.reverseOrder());
			if(vals.size() >= 5) {
				buyMap.put(key, new ArrayList<>(buyMap.get(key).subList(0,5)));
			}

		}
		
		System.out.println(buyMap.get("fb"));

		// Get Lowest Sell Prices
		for(String key: sellMap.keySet()){
			List<Double> vals = sellMap.get(key);
			Collections.sort(sellMap.get(key));
			if(vals.size() >= 5) {
				sellMap.put(key, new ArrayList<>(sellMap.get(key).subList(0,5)));
			}
		}
		System.out.println(buyMap.get("fb"));
		JSONObject state = new JSONObject();
		ArrayList<HashMap> marketInfo = new ArrayList<>();
		HashMap info = new HashMap();

		for(String key: sellMap.keySet()) {
			info.put("name", key);
			info.put("bids", buyMap.get(key));
			info.put("asks", sellMap.get(key));
			info.put("score", "score");
			
			marketInfo.add(info);
		}
	    state.put("subject", "state");
	    state.put("marketInfo", marketInfo);
				
		return state;
	    
	    /*
	      STATE
	      {subject : state, marketInfo : [{asks : [top 5 asks]}…], score}
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

STATE
{subject : state, marketInfo : [{stock, [top 5 bids], [top 5 asks]}…], score}

{
	subject: "state"
	marketInfo: [ for each element:
		{ name, [top 5], [top 5 sells], score }
				]
}

*/
