package clerk;

import javax.swing.*;

import main.SharedGetInfo;
import shipment.AssignDriver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;


public class ClerkActionsPage extends JFrame{
	
	public ClerkActionsPage() {
		super("Clerk Actions");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,800);
		setLayout(new BorderLayout(10,10));
		
		JLabel chooselb;
		
		JButton seeAllDrivers;
		JButton assignDriver;
		JButton Calculatecost;
		
		JPanel clerkOptionspnl;
		clerkOptionspnl = new JPanel();
		add(clerkOptionspnl);
		
		chooselb = new JLabel("Select the option you would like to perform");
		clerkOptionspnl.add(chooselb);
		
		assignDriver = new JButton("Assign Driver");
		clerkOptionspnl.add(assignDriver);
		
		seeAllDrivers = new JButton("Show all drivers");
		clerkOptionspnl.add(seeAllDrivers);
		
		assignDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new AssignDriver());
			}
		});
		
		
		seeAllDrivers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> alldriverList = SharedGetFromDatabase.getDriverData();
				System.out.println("this is all the drivers: "+ alldriverList);
			}
		});
		
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	


}
