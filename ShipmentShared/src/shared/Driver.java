package shared;

import java.io.Serializable;

public class Driver implements Serializable{

	public static  final long serialVersionUID = 1L;
	public String name;
	public String zone;
	
	
	public Driver(String name, String zone) {
		this.name = name;
		this.zone = zone;
	}
	
	public Driver() {
		this.name = "";
		this.zone = "";
	}
	
	public Driver(Driver d) {
		this.name = d.name;
		this.zone = d.zone;
	}

	public String getDriverName() {
		return name;
	}

	public void setDriverName(String name) {
		this.name = name;
	}

	public String getDriverZone() {
		return zone;
	}

	public void setDriverZone(String zone) {
		this.zone = zone;
	}
	
	

}
