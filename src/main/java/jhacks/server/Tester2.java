package jhacks.server;

import org.json.simple.JSONObject;

public class Tester2 {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONObject example = new JSONObject();
		example.put("Action", "Buy");
		example.put("Security", "Apple");
		example.put("Price", "10");
		example.put("OrderNumber", "1");
		String info = (String) example.get("Action");
		callAction(info);
	}
	
	public static void callAction(String string){
		System.out.print("The Action is " + string);
		return;
	}
}
