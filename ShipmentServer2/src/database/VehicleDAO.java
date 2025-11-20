package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    private final String url = "jdbc:mysql://localhost:3307/shippingdb";
    private final String user = "root";
    private final String pass = "usbw";

    // Add vehicle to DB
    public boolean addVehicleToDB(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (Brand, Model, Year, WeightCapacity, QuantityCapacity, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getYear());
            ps.setDouble(4, vehicle.getWeightCapacity());
            ps.setInt(5, vehicle.getQuantityCapacity());
            ps.setString(6, vehicle.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update vehicle in DB
    public boolean updateVehicleInDB(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET Brand=?, Model=?, Year=?, WeightCapacity=?, QuantityCapacity=?, Status=? WHERE vehicleID=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getYear());
            ps.setDouble(4, vehicle.getWeightCapacity());
            ps.setInt(5, vehicle.getQuantityCapacity());
            ps.setString(6, vehicle.getStatus());
            ps.setInt(7, vehicle.getVehicleID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all vehicles for server response
    public List<Vehicle> getAllVehiclesFromDB() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("vehicleID"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("Year"),
                        rs.getDouble("WeightCapacity"),
                        rs.getInt("QuantityCapacity"),
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}

