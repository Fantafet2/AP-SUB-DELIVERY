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

        try {
            myConn = DriverManager.getConnection(url,"root","usbw");

            System.out.println("got here (//SendToDatabase) ");

            String sql = "INSERT INTO shippingrequest "
                    + "(sendersName, recipientName, destination, dropZone, pickupZone, weight, demensions, deliveryOptions) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean sendDriverData(Driver driver) {
        String url = "jdbc:mysql://localhost:3306/shippingdb";
        Connection myConn = null;

        try {
            myConn = DriverManager.getConnection(url,"root","usbw");

            String sql = "INSERT INTO driver (driverName, driverZone) VALUES (?, ?)";
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, driver.getDriverName());
            pstmt.setString(2, driver.getDriverZone());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Driver registered successfully!");
            }
            return rows > 0;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static void sendDeliveryData(String SdriverID, String SrequestID,
                                        String driverName, String deliveryAdd,
                                        String recipientName, String type) {

        String url = "jdbc:mysql://localhost:3306/shippingdb";
        Connection myConn = null;

        String status = "Assigned";

        int driverID = Integer.parseInt(SdriverID);
        int requestID = Integer.parseInt(SrequestID);

        try {
            myConn = DriverManager.getConnection(url,"root","usbw");

            String sql = "INSERT INTO delivery (driverID, requestID, driverName, deliveryAdd, recipientName, type, status) "
                       + "VALUES(?, ?, ?, ?, ?, ?, ?)";

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
                System.out.println("✅ Delivery assigned successfully!");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean addVehicle(Vehicle v) {

        String url = "jdbc:mysql://localhost:3306/shippingdb";

        try (Connection conn = DriverManager.getConnection(url, "root", "usbw")) {

            String sql = "INSERT INTO vehicles (brand, model, year, weightCapacity, quantityCapacity, status) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, v.getBrand());
            ps.setString(2, v.getModel());
            ps.setInt(3, v.getYear());
            ps.setDouble(4, v.getWeightCapacity());
            ps.setInt(5, v.getQuantityCapacity());
            ps.setString(6, v.getStatus());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updateVehicle(Vehicle v) {

        String url = "jdbc:mysql://localhost:3306/shippingdb";

        try (Connection conn = DriverManager.getConnection(url, "root", "usbw")) {

            String sql = "UPDATE vehicles "
                       + "SET brand=?, model=?, year=?, weightCapacity=?, quantityCapacity=?, status=? "
                       + "WHERE vehicleID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, v.getBrand());
            ps.setString(2, v.getModel());
            ps.setInt(3, v.getYear());
            ps.setDouble(4, v.getWeightCapacity());
            ps.setInt(5, v.getQuantityCapacity());
            ps.setString(6, v.getStatus());
            ps.setInt(7, v.getVehicleID());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Vehicle> getAllVehicles() {
        String url = "jdbc:mysql://localhost:3306/shippingdb";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, "root", "usbw")) {
            String sql = "SELECT * FROM vehicles";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vehicle v = new Vehicle(
                    rs.getInt("vehicleID"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getDouble("weightCapacity"),
                    rs.getInt("quantityCapacity"),
                    rs.getString("status")
                );
                vehicles.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public static boolean assignVehicleSchedule(VehicleAssignmentRequest req) {
        String url = "jdbc:mysql://localhost:3306/shippingdb";
        try (Connection conn = DriverManager.getConnection(url, "root", "usbw")) {
            // Check for schedule overlap
            String checkSql = "SELECT COUNT(*) FROM schedules WHERE vehicleID=? AND NOT (end_time <= ? OR start_time >= ?)";
            PreparedStatement ps = conn.prepareStatement(checkSql);
            ps.setInt(1, req.getVehicleID());
            ps.setTimestamp(2, Timestamp.valueOf(req.getStart()));
            ps.setTimestamp(3, Timestamp.valueOf(req.getEnd()));
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) return false; // overlap exists

            // Insert schedule
            String insertSql = "INSERT INTO schedules (vehicleID, start_time, end_time, route_description, totalWeight, totalPackages) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, req.getVehicleID());
            ps.setTimestamp(2, Timestamp.valueOf(req.getStart()));
            ps.setTimestamp(3, Timestamp.valueOf(req.getEnd()));
            ps.setString(4, req.getRoute());
            ps.setDouble(5, req.getTotalWeight());
            ps.setInt(6, req.getTotalPackages());
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
