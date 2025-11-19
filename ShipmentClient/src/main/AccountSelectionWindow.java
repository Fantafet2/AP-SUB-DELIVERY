package main;

import clerk.clerkPage;
import customer.customerPage;
import driver.driverPage;
import maneger.manegerPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountSelectionWindow extends JFrame{
	
	private JButton customerButton;
	private JButton clerkButton;
	private JButton driverButton;
	private JButton manegerButton;

	public AccountSelectionWindow() {
		super("Account tyoe Selector");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLayout(new BorderLayout(10,10));
		
		JLabel titleLable = new JLabel("Select your Account type",SwingConstants.CENTER);
		titleLable.setFont(new Font("Arial", Font.BOLD, 18));

		customerButton = new JButton("customer");
		clerkButton = new JButton("clerk");
		driverButton = new JButton("Driver");
		manegerButton = new JButton ("Maneeger");
		
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        selectionPanel.add(customerButton);        
        selectionPanel.add(clerkButton);        
        selectionPanel.add(driverButton);
        selectionPanel.add(manegerButton);

        customerButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(titleLable, BorderLayout.NORTH);
        add(selectionPanel, BorderLayout.CENTER);
        
        selectionPanel.setBorder(
        	    BorderFactory.createCompoundBorder(
        	        // The LineBorder makes the visible border (1px thickness, black color)
        	        BorderFactory.createLineBorder(Color.BLACK, 18), 
        	        // The EmptyBorder adds padding *inside* the visible line
        	        BorderFactory.createEmptyBorder(20, 50, 20, 50) 
        	    )
        	);
        
        customerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clickedCustomer();
        	}
        });
        
        clerkButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clickedClerk();
        	}
        });
        
        driverButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clickedDriver();
        	}
        });
        
        manegerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clickedmaneger();
        	}
        });
        
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void clickedCustomer() {
		SwingUtilities.invokeLater(() -> new customerPage());
	}
	
	public void clickedClerk() {
		SwingUtilities.invokeLater(() -> new clerkPage());
	}
	
	public void clickedDriver() {
		SwingUtilities.invokeLater(() -> new driverPage());
	}
	
	public void clickedmaneger() {
		SwingUtilities.invokeLater(() -> new manegerPage());
	}
}
