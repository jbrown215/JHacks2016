package jhacks.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WriterTester {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket MyClient;
		MyClient = new Socket("Machine name", 18672);
		ArrayList<Socket> sockets = new ArrayList<>();
		sockets.add(MyClient);
		ServerWriter.writeTrade(sockets, "jon", 50.);
		
	}

}

