package serverf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class RequestHandler implements Runnable {

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket= socket;
	}

	@Override
	public void run() {

		try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream() ) ); 
			PrintWriter out = new PrintWriter(socket.getOutputStream());
				){
			
			String request;
			
			while((request = in.readLine()) != null) {
				String response = handleRequest(request);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
				
	}

	private String handleRequest(String msg) {

		String[] parts = msg.split("\\|");
        String command = parts[0].toUpperCase();

        switch (command) {
            case "ADD_DRIVER":
                //return SendToDatabase.sendDriverData(driverName, driverZone);;
            case "CALCULATE_COST":
                return calculateCost(parts);
            default:
                return "❌ Unknown command.";
        }	}

	private String calculateCost(String[] parts) {


		return null;
	}

	private String saveRequest(String[] parts) {
		System.out.println("this is for save ***********");
		return null;
	}

}
