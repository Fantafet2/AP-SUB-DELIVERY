package cost;

import javax.swing.*;

import java.io.FileOutputStream;
import java.io.Serializable;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import database.SendToDatabase;
import shared.SharedCustomer;
import cost.SharedInvoice;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;


public class SharedGenerateInvoice extends SharedInvoice implements Serializable{
	
	public static  final long serialVersionUID = 1L;

    public SharedGenerateInvoice(String senderName, String receiverName, String address, int deliveryZone,
                           int pickupZone, int packageWeight, String packageDemesions, String type) {
        super(senderName, receiverName, address, deliveryZone, pickupZone, packageWeight, packageDemesions, type);
    }

    public void saveAsPDF() {
        try {
            // Choose where to save
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Invoice As");
            chooser.setSelectedFile(new java.io.File("Invoice_" + senderName + ".pdf"));

            int userSelection = chooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "PDF save canceled.");
                return;
            }

            String filePath = chooser.getSelectedFile().getAbsolutePath();

            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Paragraph title = new Paragraph("INVOICE\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Invoice details
            document.add(new Paragraph("Sender: " + senderName));
            document.add(new Paragraph("Receiver: " + receiverName));
            document.add(new Paragraph("Address: " + address));
            document.add(new Paragraph("Pickup Zone: " + pickupZone));
            document.add(new Paragraph("Delivery Zone: " + deliveryZone));
            document.add(new Paragraph("Package Weight: " + packageWeight));
            document.add(new Paragraph("Package Dimensions: " + packageDemesions));           
            document.add(new Paragraph("Type: " + type));
            document.add(new Paragraph("\nThank you for using our delivery service!"));

            document.close();

            JOptionPane.showMessageDialog(null, "Invoice saved successfully at:\n" + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving PDF: " + e.getMessage());
        }
    }

    public void showInvoiceDialog() {
        String invoiceDetails =
                "📦 INVOICE DETAILS\n\n" +
                "Sender: " + senderName + "\n" +
                "Receiver: " + receiverName + "\n" +
                "Address: " + address + "\n" +
                "Pickup Zone: " + pickupZone + "\n" +
                "Delivery Zone: " + deliveryZone + "\n" +
                "Package Weight: " + packageWeight + "\n" +
                "Package Dimension: "+ packageDemesions + "\n"+
                "Delivery Option: " + type + "\n\n" +
                "Please choose what action you’d like to take:";

        Object[] options = {"Save as PDF","Create Request","Save PDF and Create Request","Cancel"};

        int choice = JOptionPane.showOptionDialog(null,invoiceDetails,"Invoice Options",JOptionPane.DEFAULT_OPTION,
        		JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);

        switch (choice) {
            case 0:
                saveAsPDF();
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Creating request for " + receiverName + "...");
                try {
                	SharedCustomer newCustomer = new SharedCustomer(senderName,receiverName, address, deliveryZone, pickupZone, packageWeight, packageDemesions, type );
                	
                	
                	SendToDatabase.sendRequestData(newCustomer );
                }catch(Exception e) {
                	e.printStackTrace();
                }
                break;
            case 2:
                saveAsPDF();
                JOptionPane.showMessageDialog(null, "Creating request and saving PDF...");
                try {
                	SharedCustomer newCustomer = new SharedCustomer(senderName,receiverName, address, deliveryZone, pickupZone, packageWeight, packageDemesions, type );
                	
                	
                	SendToDatabase.sendRequestData(newCustomer );                }catch(Exception e) {
                	e.printStackTrace();
                }
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
                break;
            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Dialog closed without selection.");
                break;
        }
    }
}

