/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelmanagementsystem;

/**
 *
 * @author dennis
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartCheckout {
    private static List<CartItem> cart = new ArrayList<>();

    public static void main(String[] args) {
         CartItem item = new CartItem("a", 10, 5);
         cart.add(item);
         CartItem item2 = new CartItem("b", 15, 6);
         cart.add(item2);
         CartItem item3 = new CartItem("c", 20, 7);
         cart.add(item3);
        updateCartPanel(DynamicItemDisplay.foodsList);
    }
    public static double totalCart(ArrayList<DynamicItemDisplay.Item> items){
        double total=0;
        for(DynamicItemDisplay.Item item : items){
            total+=item.getTotal();
        }
        return total;
    }

    public static void updateCartPanel(ArrayList<DynamicItemDisplay.Item> items) {
        JFrame frame=new JFrame("Checkout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        JPanel cart1 = new JPanel(new GridLayout(0, 1));
         cart1.setSize(100,100);
        cart1.removeAll();
        JPanel cart2 = new JPanel(new GridLayout(0,7));
         cart2.setPreferredSize(new Dimension(100,80));
        cart2.add(new JLabel("Item Name"));
        cart2.add(new JLabel("Price"));
        cart2.add(new JLabel("Quantity"));
        cart2.add(new JLabel("Total"));
        cart2.add(new JLabel("remove"));
        cart2.add(new JLabel("increase"));
        cart2.add(new JLabel("decrease"));
        cart1.add(cart2,BorderLayout.CENTER);
        JPanel totalofCart=new JPanel(new FlowLayout());
        JLabel startTotal=new JLabel("Total");
        JLabel totalOfCart=new JLabel(String.valueOf(totalCart(DynamicItemDisplay.foodsList)));
        JButton pay=new JButton("Pay");
        pay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PaymentFormGUI p=new PaymentFormGUI();
                    p.setVisible(true);  
                }
            });
        
        totalofCart.add(startTotal);
        totalofCart.add(totalOfCart);    
        totalofCart.add(pay);   
        frame.add(cart1);
        frame.add(totalofCart,BorderLayout.SOUTH);
        frame.setVisible(true);
        
        
        

        for (DynamicItemDisplay.Item item : items) {
           JPanel cartPanel2 = new JPanel(new GridLayout(0,7));
             cartPanel2.setSize(30,30);
            JLabel nameLabel = new JLabel(item.getName());
            JLabel priceLabel = new JLabel(String.valueOf(item.getPrice()));
            JLabel quantityLabel = new JLabel(String.valueOf(item.getQuantity()));
            JLabel totalLabel = new JLabel(String.valueOf(item.getTotal()));

            JButton removeButton = new JButton("Remove");
            JButton increaseButton = new JButton("+");
            JButton decreaseButton = new JButton("-");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DynamicItemDisplay.foodsList.remove(item);
                    updateCartPanel(DynamicItemDisplay.foodsList);
                    totalOfCart.setText(String.valueOf(totalCart(DynamicItemDisplay.foodsList)));
                }
            });
             increaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int newquantity=item.getQuantity()+1;
                    item.setQuantity(newquantity);
                    quantityLabel.setText(Integer.toString(newquantity)); 
                    totalLabel.setText(String.valueOf(item.getTotal()));
                    totalOfCart.setText(String.valueOf(totalCart(DynamicItemDisplay.foodsList)));
                }
            });
             decreaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int newquantity=item.getQuantity()-1;
                    if(newquantity==0){
                        DynamicItemDisplay.foodsList.remove(item);
                        updateCartPanel(DynamicItemDisplay.foodsList);    
                        totalOfCart.setText(String.valueOf(totalCart(DynamicItemDisplay.foodsList)));
                    }
                    else{
                        item.setQuantity(newquantity);
                        quantityLabel.setText(Integer.toString(newquantity)); 
                        totalLabel.setText(String.valueOf(item.getTotal()));
                        totalOfCart.setText(String.valueOf(totalCart(DynamicItemDisplay.foodsList)));
                    }
                }
            });

            cartPanel2.add(nameLabel);
            cartPanel2.add(priceLabel);
            cartPanel2.add(quantityLabel);
            cartPanel2.add(totalLabel);
            cartPanel2.add(removeButton);
            cartPanel2.add(increaseButton);
            cartPanel2.add(decreaseButton);
            cart1.add(cartPanel2,BorderLayout.CENTER);
        }
        cart1.revalidate();
        cart1.repaint();

        
    }
}

class CartItem {
    private String name;
    private double price;
    private int quantity;

    public CartItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return price * quantity;
    }
}



