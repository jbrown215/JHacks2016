package jhacks.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

import org.json.simple.JSONObject;

public class Tester {

	public static void main(String[] args) {
		// Test data
		Map<String, List<Double>> buyMap = new HashMap<>();
		List<Double> test = new ArrayList<>();
		test.add(5.); test.add(9.); test.add(41.); test.add(14.);
		test.add(60.);
		test.add(3.);
		test.add(20.);
		List<Double> test2 = new ArrayList<>();
		test2.add(9.); //test2.add(7.); test2.add(50.); test2.add(41.); test2.add(14.);

		buyMap.put("fb", test);
		buyMap.put("apple",test);
		buyMap.put("google", test2);
		Map sellMap = buyMap;
		Map state = Main.sendStateMessage(buyMap, sellMap);
//		JSONObject trade = Main.sendTradeMessage("fb", 5.);
		System.out.println(state);
	}
}
