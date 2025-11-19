/*
 * netstat -ano | findstr :9393
 * taskkill /F /PID
 */

package serverf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shared.ClientRequest;
import shared.Driver;
import shared.ServerResponse;
import shared.SharedCustomer;

import database.SendToDatabase;

public class Server {
    private static ExecutorService pool = Executors.newCachedThreadPool();

	
	public static void main(String[] args) {
		
		final int SERVER_PORT = 9393;
		Socket socket = null;
		
		BufferedReader  bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		ServerSocket serverSocket = null;
		
		
		
		try {
			 serverSocket = new ServerSocket(SERVER_PORT );
	         System.out.println("Server started on port " + SERVER_PORT);
	            System.out.println("From shipment_server/server/Server.java " );

	    		while(true) {
	    			
	    			Socket clientSocket = serverSocket.accept();
	    			System.out.println("✅ New connection from " + clientSocket.getInetAddress());
	                new Thread(() -> {
						try {
							handleClient(clientSocket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}).start();
	    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	private static void handleClient(Socket clientSocket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        //Receives the object sent by the client and sees what function should be done with it 
        try {
            Object received = in.readObject();

            if(received instanceof ClientRequest req) {
            	switch (req.getAction()) {
            	
            	case "ADD_DRIVER" ->{
            		Driver d = (Driver) req.getData();
            		boolean ok = SendToDatabase.sendDriverData(d);
                    out.writeObject(new ServerResponse(ok, "Driver added"));
            	}
            	
            	case "GET_QUOTE" ->{
            		SharedCustomer customer = (SharedCustomer) req.getData();
            		boolean ok = SendToDatabase.sendRequestData(customer);
                    out.writeObject(new ServerResponse(ok, "Request added"));
            		System.out.println("Object being sent: " + customer);

            	}
            	
            	
            }
          }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                // ✅ Close cleanly after data is transmitted
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
