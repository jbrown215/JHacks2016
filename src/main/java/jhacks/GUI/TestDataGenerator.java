package jhacks.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javax.swing.Timer;

public class TestDataGenerator implements Runnable {

	private Queue<Map<String, Double>> queue;
	private static final Random random = new Random();
	public TestDataGenerator(Queue<Map<String, Double>> queue) {
		this.queue = queue;
	}
	@Override
	public void run() {
		
		Timer timer = new Timer(500, new ActionListener() {
			  @Override
			  public void actionPerformed(ActionEvent arg0) {
				  Map<String, Double> map = new HashMap<>();
				  map.put("price", random.nextDouble() * 100);
				  queue.offer(map);
			  }
			});
			timer.setRepeats(true); // Only execute once
			timer.start(); // Go go go!
	}

}
