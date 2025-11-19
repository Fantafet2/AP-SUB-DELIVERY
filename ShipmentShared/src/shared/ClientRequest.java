package shared;

import java.io.Serializable;

public class ClientRequest implements Serializable{

	private String action;
	private Object data;
	
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
