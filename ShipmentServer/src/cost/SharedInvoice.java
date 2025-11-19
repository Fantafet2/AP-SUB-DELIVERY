package cost;

import java.io.Serializable;

import javax.swing.SwingUtilities;

public class SharedInvoice implements Serializable{
    private static final long serialVersionUID = 1L;
	
	public String senderName;
	public String receiverName;
	public String address;
	public int deliveryZone;
	public int pickupZone; 
	public int packageWeight;
	public String packageDemesions;
	public String type;
	public int cost;
	

	
	public SharedInvoice(String senderName, String receiverName, String address, int deliveryZone, int pickupZone,
			int packageWeight, String packageDemesions, String type) {
		super();
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.address = address;
		this.deliveryZone = deliveryZone;
		this.pickupZone = pickupZone;
		this.packageWeight = packageWeight;
		this.packageDemesions = packageDemesions;
		this.type = type;
		
		try {
			this.cost = SharedCalculateCost.calculateCost(packageWeight, type, deliveryZone);
		}
		catch (NumberFormatException e) {
			
            System.err.println("Error calculating cost: Invalid number format for zone or weight.");
            this.cost = 0; 
        }
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDeliveryZone() {
		return deliveryZone;
	}

	public void setDeliveryZone(int deliveryZone) {
		this.deliveryZone = deliveryZone;
	}

	public int getPickupZone() {
		return pickupZone;
	}

	public void setPickupZone(int pickupZone) {
		this.pickupZone = pickupZone;
	}

	public int getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(int packageWeight) {
		this.packageWeight = packageWeight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public void createInvoice() {

		SwingUtilities.invokeLater(() -> {
			SharedGenerateInvoice g = new SharedGenerateInvoice(
		        senderName, receiverName, address, deliveryZone,
		        pickupZone, packageWeight, packageDemesions, type
		    );
		    g.showInvoiceDialog();
		});

	}
	

}

