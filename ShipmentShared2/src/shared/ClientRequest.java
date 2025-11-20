package shared;

import java.io.Serializable;

public class ClientRequest implements Serializable{

	private String action;
	private Object data;
	
	public static final String ADD_VEHICLE = "ADD_VEHICLE";
	public static final String UPDATE_VEHICLE = "UPDATE_VEHICLE";
	public static final String ASSIGN_VEHICLE = "ASSIGN_VEHICLE";
	public static final String GET_VEHICLES = "GET_VEHICLES";
	
	public ClientRequest(String action, Object data) {
		this.action = action;
		this.data = data;
	}
	

	public String getAction() {
		return action;
	}
	
	public Object getData() {
		return data;
	}
}
