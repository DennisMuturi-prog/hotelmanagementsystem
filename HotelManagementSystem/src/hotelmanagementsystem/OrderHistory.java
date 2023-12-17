/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelmanagementsystem;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dennis
 */
public class OrderHistory extends JFrame{
    private static DefaultTableModel tableModel;
    private JTable itemTable;
    public OrderHistory(){
        setTitle("My Order History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the table model and add columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("OrderID");

        // Create the item table
        itemTable = new JTable(tableModel);

        // Create a JScrollPane to hold the item table
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        add(tableScrollPane, BorderLayout.CENTER);
        JPanel back=new JPanel();
        JButton backBtn=new JButton("Back to Menu");
        backBtn.addActionListener(e -> {
            DynamicItemDisplay d=new DynamicItemDisplay();
            d.setVisible(true);
            this.dispose();     
            });
        back.add(backBtn);
        add(back,BorderLayout.SOUTH);
        showOrderHistory();
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderHistory o= new OrderHistory();
            o.setVisible(true);
        });
    }
    public static void showOrderHistory(){
        

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false","root","");
            Statement stm = con.createStatement();
            Login_page l=new Login_page();
            String customername=l.getCustomername();
            String sql="SELECT Customername,FoodName,FoodQuantity,OrderID FROM restaurantcustomers r JOIN foodorders f ON r.customerID = f.IDCustomer JOIN menuitems m ON f.IDFOOD = m.FoodID WHERE Customername='"+customername+"' ORDER BY OrderID DESC;";
            ResultSet rs=stm.executeQuery(sql);
            while(rs.next()){
                String foodname=rs.getString("FoodName");
                String foodquantity=String.valueOf(rs.getInt("FoodQuantity"));
                String orderid=String.valueOf(rs.getInt("OrderID"));
                String[] tbdata={foodname,foodquantity,orderid};
                System.out.println(tbdata);
                tableModel.addRow(tbdata);    
            }
            
            con.close();     
        }catch(Exception e){
            System.out.println(e.getMessage());}
    
    }
    
    
}
