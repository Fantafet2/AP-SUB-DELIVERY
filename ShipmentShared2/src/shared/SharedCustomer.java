package shared;

import java.io.Serializable;

public class SharedCustomer implements Serializable{
	
	public static  final long serialVersionUID = 1L;
	
	public String senderName;
	public String receivername;
	public String address;
	public int dropoffZone;
	public int pickupZone;
	public int weight;
	public String demessions;
	public String deliveryOption;
	
	
	public SharedCustomer(String senderName, String receivername, String address, int dropoffZone, int pickupZone,
			int weight, String demessions, String deliveryOption) {
		this.senderName = senderName;
		this.receivername = receivername;
		this.address = address;
		this.dropoffZone = dropoffZone;
		this.pickupZone = pickupZone;
		this.weight = weight;
		this.demessions = demessions;
		this.deliveryOption = deliveryOption;
	}
	
	public SharedCustomer() {
		this.senderName = "";
		this.receivername = "";
		this.address = "";
		this.dropoffZone = 0;
		this.pickupZone = 0;
		this.weight = 0;
		this.demessions = "";
		this.deliveryOption = "";
	}
	
	public SharedCustomer(SharedCustomer SC) {
		this.senderName = SC.senderName;
		this.receivername = SC.receivername;
		this.address = SC.address;
		this.dropoffZone = SC.dropoffZone;
		this.pickupZone = SC.pickupZone;
		this.weight = SC.weight;
		this.demessions = SC.demessions;
		this.deliveryOption = SC.deliveryOption;
	}
	
	

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDropoffZone() {
		return dropoffZone;
	}

	public void setDropoffZone(int dropoffZone) {
		this.dropoffZone = dropoffZone;
	}

	public int getPickupZone() {
		return pickupZone;
	}

	public void setPickupZone(int pickupZone) {
		this.pickupZone = pickupZone;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDemessions() {
		return demessions;
	}

	public void setDemessions(String demessions) {
		this.demessions = demessions;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	
	@Override
	public String toString() {
	    return "SharedCustomer{" +
	            
	            "senderName='" + senderName + "' (" + senderName.getClass().getSimpleName() + ')' +
	            ", receivername='" + receivername + "' (" + receivername.getClass().getSimpleName() + ')' +
	            ", address='" + address + "' (" + address.getClass().getSimpleName() + ')' +
	            
	            ", dropoffZone=" + dropoffZone + " (" + Integer.TYPE.getName() + ')' +
	            ", pickupZone=" + pickupZone + " (" + Integer.TYPE.getName() + ')' +
	            ", weight=" + weight + " (" + Integer.TYPE.getName() + ')' +
	            
	            ", demessions='" + demessions + "' (" + demessions.getClass().getSimpleName() + ')' +
	            ", deliveryOption='" + deliveryOption + "' (" + deliveryOption.getClass().getSimpleName() + ')' +
	            
	            '}';
	}
	
}
