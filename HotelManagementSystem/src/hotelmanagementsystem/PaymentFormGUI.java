/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelmanagementsystem;

/**
 *
 * @author dennis
 */

import static hotelmanagementsystem.ShoppingCartCheckout.totalCart;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PaymentFormGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable itemTable;

    public PaymentFormGUI(){
        setTitle("Payment Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the table model and add columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Unit Price");
        tableModel.addColumn("Total");

        // Create the item table
        itemTable = new JTable(tableModel);

        // Create a JScrollPane to hold the item table
        JScrollPane tableScrollPane = new JScrollPane(itemTable);

        // Create text fields for item details
   

        // Create a button to add items
        for(DynamicItemDisplay.Item item : DynamicItemDisplay.foodsList){
                String itemName = item.getName();
                int quantity = item.getQuantity();
                double unitPrice =item.getPrice();
                double total = quantity * unitPrice;

                tableModel.addRow(new Object[]{itemName, quantity, unitPrice, total});
        
        }
   // Update the grand total label
            

        // Create a button to confirm payment
        JButton confirmButton = new JButton("Confirm Payment");
        
        confirmButton.addActionListener(e -> {
             JOptionPane.showMessageDialog(null, "Payment confirmed!");
                addOrders(DynamicItemDisplay.foodsList); 
                DynamicItemDisplay d=new DynamicItemDisplay();
                d.setVisible(true);
                this.dispose();
                
            });

        // Create a panel for item details
       

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        JPanel grandTotalPanel = new JPanel();
        JLabel grandT=new JLabel("GrandTotal");
        JLabel grandTally=new JLabel(String.valueOf(totalCart(DynamicItemDisplay.foodsList)));
        Login_page l=new Login_page();
        double coupon=couponGenerator(l.getCustomername());
        JButton applyCoupon=new JButton("APPLY ksh "+coupon+" Loyalty coupon");
        applyCoupon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the payment confirmation here
                
                grandTally.setText(String.valueOf(totalCart(DynamicItemDisplay.foodsList)-coupon));  
                JOptionPane.showMessageDialog(null, "Coupon confirmed!Coupons are calculated with your past expenditure/40");
            }
        });
        grandTotalPanel.add(grandT);
        grandTotalPanel.add(grandTally);
        grandTotalPanel.add(applyCoupon);
        
        
        buttonPanel.add(confirmButton);

        // Set up the layout
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);
        add(grandTotalPanel,BorderLayout.EAST);
        add(buttonPanel,BorderLayout.SOUTH);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaymentFormGUI paymentForm = new PaymentFormGUI();
            paymentForm.setVisible(true);
        });
    }
    public static double couponGenerator(String userName){
        double expenditure=0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false","root","");
            Statement stm = con.createStatement();
            String sql="select customerexpenditure from restaurantcustomers where Customername='"+userName+"'";
            ResultSet rs=stm.executeQuery(sql);
            if(rs.next()){
                expenditure=rs.getDouble("customerexpenditure")/50;
            }
            
            
            }catch(Exception e){
            System.out.println(e.getMessage());} 
        if(expenditure>10){
            return expenditure;
        }
        return 0;
          
    }
    public static void addOrders(ArrayList<DynamicItemDisplay.Item> items){
        int customerId=0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false","root","");
            Statement stm = con.createStatement();
            Login_page l=new Login_page();
            String customername=l.getCustomername();
            if(customername==null){
                customername="guest";
            }
            System.out.println(customername);
            String sql="select customerID from restaurantcustomers where Customername='"+customername+"' ";
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next()) {
             customerId=rs.getInt("customerID");
             System.out.println(customerId);
            } 
            con.close();     
        }catch(Exception e){
            System.out.println(e.getMessage());}
        for(DynamicItemDisplay.Item item : items){
            int Id=0;
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false","root","");
            Statement stm = con.createStatement();
            String foodname=item.getName();
            String sql="select FoodID from menuitems where FoodName='"+foodname+"' ";
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next()) {
             Id=rs.getInt("FoodID");
             System.out.println(Id);
            } 
            con.close();     
        }catch(Exception e){
            System.out.println(e.getMessage());}
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false","root","");
            Statement stm = con.createStatement();
            int foodQuantity=item.getQuantity();
            System.out.println(foodQuantity);
             
            String sql="insert into foodorders(FoodQuantity,IDFOOD,IDCustomer) values("+foodQuantity+","+Id+","+customerId+") ";
            System.out.println(sql);
            stm.executeUpdate(sql);
            con.close();     
        }catch(Exception e){
            System.out.println(e.getMessage());}    
        }
        
        
       
        
    }
}




