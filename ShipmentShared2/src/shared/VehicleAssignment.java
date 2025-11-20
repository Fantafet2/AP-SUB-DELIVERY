package shared;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VehicleAssignment implements Serializable {

	private static final long serialVersionUID = 1L;
	private int vehicleID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String route;
    private int packageQty;
    private double totalWeight;

    public VehicleAssignment(int vehicleID, LocalDateTime startTime, LocalDateTime endTime,
                             String route, int packageQty, double totalWeight) {

        this.vehicleID = vehicleID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.route = route;
        this.packageQty = packageQty;
        this.totalWeight = totalWeight;
    }

    public int getVehicleID() { return vehicleID; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getRoute() { return route; }
    public int getPackageQty() { return packageQty; }
    public double getTotalWeight() { return totalWeight; }
}
