package database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
	private final String url = "jdbc:mysql://localhost:3307/shippingdb";
    private final String user = "root";
    private final String pass = "usbw";

    public boolean hasOverlap(int vehicleID, LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = "SELECT COUNT(*) FROM schedules WHERE vehicleID = ? AND NOT (end_time <= ? OR start_time >= ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleID);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void createSchedule(int vehicleID, LocalDateTime start, LocalDateTime end, String route) throws SQLException {
        String sql = "INSERT INTO schedules (vehicleID, start_time, end_time, route_description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleID);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(end));
            ps.setString(4, route);
            ps.executeUpdate();
        }
    }

    public List<String> getVehicleSchedules(int vehicleID) throws SQLException {
        List<String> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules WHERE vehicleID = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    schedules.add("Schedule " + rs.getInt("scheduleID") +
                            ": " + rs.getTimestamp("start_time") +
                            " to " + rs.getTimestamp("end_time") +
                            " | Route: " + rs.getString("route_description"));
                }
            }
        }
        return schedules;
    }

}
