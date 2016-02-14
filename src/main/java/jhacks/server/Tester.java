package jhacks.server;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Tester {

	public static void main(String[] args) {
		// Test data
		Map<String, Double> buyMap = new HashMap<String,Double>();
		buyMap.put("fb", new Double(10));
		buyMap.put("apple", new Double(5));
		buyMap.put("google", new Double(3));
		buyMap.put("adi", new Double(8));
		buyMap.put("jon", new Double(4));
		buyMap.put("ayelet", new Double(15));
		buyMap.put("joardan", new Double(13));
				
		Map state = Main.sendStateMessage(buyMap);
	}
}
