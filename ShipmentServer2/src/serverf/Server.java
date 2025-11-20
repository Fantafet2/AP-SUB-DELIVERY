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
import shared.Vehicle;
import vehicles.VehicleDAO;

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

	    try {
	        Object received = in.readObject();

	        if (received instanceof ClientRequest req) {
	            switch (req.getAction()) {

	                case "ADD_DRIVER" -> {
	                    Driver d = (Driver) req.getData();
	                    boolean ok = SendToDatabase.sendDriverData(d);
	                    out.writeObject(new ServerResponse(ok, "Driver added"));
	                }

	                case "GET_QUOTE" -> {
	                    SharedCustomer customer = (SharedCustomer) req.getData();
	                    boolean ok = SendToDatabase.sendRequestData(customer);
	                    out.writeObject(new ServerResponse(ok, "Request added"));
	                }

	                // --- VEHICLE CASES ---
	                case "ADD_VEHICLE" -> {
	                    Vehicle vehicle = (Vehicle) req.getData();
	                    VehicleDAO dao = new VehicleDAO();
	                    boolean ok = dao.addVehicleToDB(vehicle); // new method in DAO
	                    out.writeObject(new ServerResponse(ok, ok ? "Vehicle added" : "Failed to add vehicle"));
	                }

	                case "UPDATE_VEHICLE" -> {
	                    Vehicle vehicle = (Vehicle) req.getData();
	                    VehicleDAO dao = new VehicleDAO();
	                    boolean ok = dao.updateVehicleInDB(vehicle); // new method in DAO
	                    out.writeObject(new ServerResponse(ok, ok ? "Vehicle updated" : "Failed to update vehicle"));
	                }

	                case "GET_VEHICLES" -> {
	                    VehicleDAO dao = new VehicleDAO();
	                    List<Vehicle> vehicles = dao.getAllVehiclesFromDB(); // modified DAO method
	                    out.writeObject(new ServerResponse(true, "Vehicles retrieved", vehicles));
	                }
	                
	                case "ASSIGN_VEHICLE" -> {
	                    VehicleAssignmentRequest assignment = (VehicleAssignmentRequest) req.getData();
	                    ScheduleDAO scheduleDAO = new ScheduleDAO();
	                    boolean ok = false;
	                    String message = "";
	                    try {
	                        if(scheduleDAO.hasOverlap(assignment.getVehicleID(), assignment.getStart(), assignment.getEnd())){
	                            message = "Error: Vehicle has overlapping schedule!";
	                        } else {
	                            scheduleDAO.createSchedule(
	                                    assignment.getVehicleID(),
	                                    assignment.getStart(),
	                                    assignment.getEnd(),
	                                    assignment.getRoute()
	                            );
	                            ok = true;
	                            message = "Vehicle assigned successfully!";
	                        }
	                    } catch(Exception e){
	                        e.printStackTrace();
	                        message = "Server error: " + e.getMessage();
	                    }
	                    out.writeObject(new ServerResponse(ok, message));
	                }

	                case "GET_SCHEDULES" -> {
	                    int vehicleID = (int) req.getData();
	                    ScheduleDAO scheduleDAO = new ScheduleDAO();
	                    try {
	                        List<String> schedules = scheduleDAO.getVehicleSchedules(vehicleID);
	                        out.writeObject(new ServerResponse(true, "Schedules retrieved", schedules));
	                    } catch (Exception e){
	                        e.printStackTrace();
	                        out.writeObject(new ServerResponse(false, "Error retrieving schedules: " + e.getMessage()));
	                    }
	                }
	            }

	            }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (in != null) in.close();
	            if (out != null) out.close();
	            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
}
