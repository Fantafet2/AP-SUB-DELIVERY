package shared;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VehicleAssignmentRequest implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private int vehicleID;
    private LocalDateTime start;
    private LocalDateTime end;
    private String route;
    private int totalPackages;
    private double totalWeight;

    public VehicleAssignmentRequest(int vehicleID, LocalDateTime start, LocalDateTime end, 
                                    String route, int totalPackages, double totalWeight) {
        this.vehicleID = vehicleID;
        this.start = start;
        this.end = end;
        this.route = route;
        this.totalPackages = totalPackages;
        this.totalWeight = totalWeight;
    }

    public int getVehicleID() { return vehicleID; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public String getRoute() { return route; }
    public int getTotalPackages() { return totalPackages; }
    public double getTotalWeight() { return totalWeight; }
}
