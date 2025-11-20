package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SharedGetFromDatabase {
    
    private static final String URL = "jdbc:mysql://localhost:3306/shippingdb";
    private static final String USER = "root";
    private static final String PASS = ""; 
    
    
    public static List<String> getDriversByZone(String userZone) {
        Connection myConn = null;
        PreparedStatement pstmt = null;
        ResultSet myRs = null;
        List<String> driverList = new ArrayList<>();

        try {
            myConn = DriverManager.getConnection(URL, USER, PASS);
            // Use a prepared statement for safety
            String sql = "SELECT DID, driverName, driverZone FROM driver WHERE driverZone = ?";
            pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, userZone);

            myRs = pstmt.executeQuery();

            // Process results
            while (myRs.next()) {
                int id = myRs.getInt("DID");
                String name = myRs.getString("driverName");
                String zone = myRs.getString("driverZone");

                driverList.add("Driver ID: " + id + ", Name: " + name + ", Zone: " + zone);
            }


            return driverList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(myConn, pstmt, myRs);
        }
    }
    
    
    public static String getZoneByTransactionId(String requestID) {
        Connection myConn = null;
        PreparedStatement pstmt = null; // Use PreparedStatement for security
        ResultSet myRs = null;
        String zoneNumber = null; // Variable to hold the single result

        try {
            myConn = DriverManager.getConnection(URL, USER, PASS);

            // 1. Modified SQL: SELECT only 'zoneoptions' and filter by 'ReqID' using a placeholder (?)
            String sql = "SELECT deliveryzoneoptions FROM shippingrequest WHERE ReqID = ?";
            
            pstmt = myConn.prepareStatement(sql);
            
            // 2. Set the parameter: Bind the input ID to the placeholder
            pstmt.setString(1, requestID);

            // 3. Execute the query
            myRs = pstmt.executeQuery();

            // 4. Process the single result
            if (myRs.next()) {
                // Retrieve ONLY the zone number by column name
                zoneNumber = myRs.getString("deliveryzoneoptions");
            }
            
            // Return the zone number (will be null if the ID was not found)
            return zoneNumber; 

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null on failure
        } finally {
            // 5. Close resources (assuming close method is available)
            close(myConn, pstmt, myRs);
        }
    }


    public static List<String> getDriverData() {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        List<String> driverList = new ArrayList<>();
        
        try {
            // 1. Establish the connection
            myConn = DriverManager.getConnection(URL, USER, PASS);

            // 2. Create the SQL statement
            myStmt = myConn.createStatement();
            String sql = "SELECT DID, driverName, driverZone FROM driver";
            
            // 3. Execute the query and get the result set
            myRs = myStmt.executeQuery(sql);

            // 4. Process the result set
            while (myRs.next()) {
                // Retrieve data by column name
            	String id = myRs.getString("DID");
                String name = myRs.getString("driverName");
                String zone = myRs.getString("driverZone");
                
                // Add the combined info to your list
                driverList.add("ID: "+ id +"Name: " + name + ", Zone: " + zone);
            }
            
            return driverList;

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null or empty list on failure
        } finally {
            // 5. Close resources to prevent memory leaks (CRUCIAL step)
            close(myConn, myStmt, myRs);
        }
    }
    
    
    public static List<String> getRequestData(String userZone) {
        Connection myConn = null;
        ResultSet myRs = null;
        // Only need PreparedStatement (and Connection, ResultSet)
        PreparedStatement pstmt = null; 

        List<String> RequestList = new ArrayList<>();
        
        try {
            // 1. Establish the connection
            myConn = DriverManager.getConnection(URL, USER, PASS);

            // 2. Create the SQL statement with the placeholder
            String sql = "SELECT ReqID, receivererName, packageweight, deliveryoption, destination, deliveryzoneoptions, pickupzoneoptions FROM shippingrequest WHERE deliveryzoneoptions = ?";
            
            // Use PreparedStatement
            pstmt = myConn.prepareStatement(sql);
            
            // 3. Set the parameter value for the placeholder (?)
            pstmt.setString(1, userZone); 
            
            // 4. Execute the prepared statement (DO NOT pass the SQL string)
            myRs = pstmt.executeQuery(); // <-- CORRECT EXECUTION

            // 5. Process the result set
            while (myRs.next()) {
                // ... (Rest of your result processing code is correct)
                String id = myRs.getString("ReqID");
                String name = myRs.getString("receivererName");
                String weight = myRs.getString("packageweight");
                String option = myRs.getString("deliveryoption");
                String address = myRs.getString("destination");
                String deliveryzone = myRs.getString("deliveryzoneoptions");
                String pickupzone = myRs.getString("pickupzoneoptions");
                
                RequestList.add("Reques ID: " + id +" Name: "+ name + " Weight: " + weight + " Type: "+ option + " Address: " + address +" Delivery Zone: "  + deliveryzone + " Pickup Zone "+ pickupzone );
            }
            
            return RequestList;

        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        } finally {
            // 6. Close resources (use the correct close method for PreparedStatement)
            close(myConn, pstmt, myRs); // Assuming your close method handles PreparedStatement/Statement
        }
    }
    
    // Helper method to close resources quietly
    private static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}