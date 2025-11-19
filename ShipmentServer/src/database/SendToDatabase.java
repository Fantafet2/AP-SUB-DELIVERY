package database;

import java.sql.*;
import java.util.Scanner;

import javax.swing.JTextField;
import shared.Driver;
import shared.SharedCustomer;


public class SendToDatabase {

 
	public static boolean sendRequestData(SharedCustomer customer) {
		String url = "jdbc:mysql://localhost:3306/shippingdb";
		Connection myConn = null;
		

		try{
			myConn = DriverManager.getConnection(url,"root","usbw");
			
			System.out.println("got here (//SendToDatabase) ");
	            // Use a prepared statement to avoid SQL injection
	            String sql = "INSERT INTO shippingrequest (sendersName, recipientName, destination, dropZone, pickupZone, weight,demensions, deliveryOptions) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement pstmt = myConn.prepareStatement(sql);
	            pstmt.setString(1, customer.getSenderName());
	            pstmt.setString(2, customer.getReceivername());
	            pstmt.setString(3, customer.getAddress());
	            pstmt.setInt(4, customer.getDropoffZone());
	            pstmt.setInt(5, customer.getPickupZone());
	            pstmt.setInt(6, customer.getWeight());
	            pstmt.setString(7, customer.getDemessions());
	            pstmt.setString(8, customer.getDeliveryOption());


	            int rows = pstmt.executeUpdate();

	            if (rows > 0) {
	                System.out.println("✅ User registered successfully!");
	            }

			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	
	}

	public static boolean sendDriverData(Driver driver) {
		String url = "jdbc:mysql://localhost:3306/shippingdb";
		Connection myConn = null;
		
		try{
			myConn = DriverManager.getConnection(url,"root","usbw");
			
	            // Use a prepared statement to avoid SQL injection
	            String sql = "INSERT INTO driver (driverName, driverZone) VALUES (?, ?)";
	            PreparedStatement pstmt = myConn.prepareStatement(sql);
	            pstmt.setString(1, driver.getDriverName());
	            pstmt.setString(2, driver.getDriverZone());
	            
	            
	            int rows = pstmt.executeUpdate();

	            if (rows > 0) {
	                System.out.println("✅ Driver registered successfully!");
	            }
	            return rows > 0;

			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}
	

	public static void sendDeliveryData(String SdriverID, String SrequestID, String driverName, String deliveryAdd,String recipientName, String type) {
		String url = "jdbc:mysql://localhost:3306/shippingdb";
		Connection myConn = null;
		
		String status = "Assigned";
				
		int driverID = Integer.parseInt(SdriverID);
		int requestID = Integer.parseInt(SrequestID);
		
		try {
			myConn = DriverManager.getConnection(url,"root","usbw");
			
			String sql = "INSERT INTO delivery (driverID, requestID, driverName, deliveryAdd, recipientName, type, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = myConn.prepareStatement(sql);
			pstmt.setInt(1, driverID);
			pstmt.setInt(2, requestID);
			pstmt.setString(3, driverName);
			pstmt.setString(4, deliveryAdd);
			pstmt.setString(5, recipientName);
			pstmt.setString(6, type);
			pstmt.setString(7, status);
			
            int rows = pstmt.executeUpdate();

			if (rows > 0) {
                System.out.println("✅ Driver registered successfully!");
            }


		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
