package cost;

public class SharedCalculateCost {

	public static int Calculatezone(int sender, int receiver) {
		
		int zoneMultiplier = 0;
		
		if (receiver > sender ) {
			zoneMultiplier = receiver-sender+1;
		}else if (sender > receiver) {
			zoneMultiplier = sender-receiver+1;
		}else {
			zoneMultiplier = 1;
		}
		
		System.out.println("this is zone multiplier: "+zoneMultiplier);
		return zoneMultiplier;
		
	}
	
	public static int calculateCost(int weight, String type, int zoneMultiplier) {
		
		int charge = 0;
		int totalCost = 0;
		int typeCharge = 0;
		int weightMultiplier = weight / 100;
		
		if(type == "standard" || type == "Standard") {
			typeCharge = 500;
		}else if (type == "express" || type == "Express") {
			typeCharge = 1500;
		}else if (type == "fragile" || type == "Fragile") {
			typeCharge = 2000;
		}
		
		charge = weightMultiplier * zoneMultiplier;
		totalCost = charge + typeCharge;
		
		return totalCost;
	}
}
