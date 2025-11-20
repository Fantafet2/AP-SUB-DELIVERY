package shared;

import java.io.Serializable;

public class Vehicle implements Serializable{
	
	public static  final long serialVersionUID = 1L;
	
	private int vehicleID;
    private String brand;
    private String model;
    private int year;
    private double weightCapacity;
    private int quantityCapacity;
    private String status;

    public Vehicle(int vehicleID, String brand, String model, int year,
                   double weightCapacity, int quantityCapacity, String status) {
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.weightCapacity = weightCapacity;
        this.quantityCapacity = quantityCapacity;
        this.status = status;
    }

    public Vehicle(String brand, String model, int year,
                   double weightCapacity, int quantityCapacity, String status) {
        this(0, brand, model, year, weightCapacity, quantityCapacity, status);
    }

    // Getters and setters
    public int getVehicleID() { return vehicleID; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getWeightCapacity() { return weightCapacity; }
    public int getQuantityCapacity() { return quantityCapacity; }
    public String getStatus() { return status; }

    public void setVehicleID(int vehicleID) { this.vehicleID = vehicleID; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setWeightCapacity(double weightCapacity) { this.weightCapacity = weightCapacity; }
    public void setQuantityCapacity(int quantityCapacity) { this.quantityCapacity = quantityCapacity; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return brand + " " + model + " (" + year + ")";
    }

}
