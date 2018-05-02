package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable {
	private Socket socket;
	public ServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	
	public void run() {
		BufferedReader bufferedReader = null;
		PrintWriter writer = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(),true);
			while(true) {
				String info = bufferedReader.readLine();
				if(info == null)
					break;
				System.out.println("the client is sending message:" + info);
				writer.println("the server is accepting the request");
			}
		}
		catch(Exception e) {
			
		}
	}

}
