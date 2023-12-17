/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelmanagementsystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDisplayBean {

    private ArrayList<Item> items;
    private int itemsInCart;

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ItemDisplayBean() {
        items = new ArrayList<>();
        itemsInCart = 0;
        fetchItemsFromDatabase(); // Fetch items from the database during initialization
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getItemsInCart() {
        return itemsInCart;
    }

    public void addItemToCart(Item item) {
        items.add(item);
        setItemsInCart(itemsInCart + 1);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private void setItems(ArrayList<Item> newItems) {
        ArrayList<Item> oldItems = this.items;
        this.items = newItems;
        propertyChangeSupport.firePropertyChange("items", oldItems, newItems);
    }

    private void setItemsInCart(int newItemsInCart) {
        int oldItemsInCart = this.itemsInCart;
        this.itemsInCart = newItemsInCart;
        propertyChangeSupport.firePropertyChange("itemsInCart", oldItemsInCart, newItemsInCart);
    }

    private void fetchItemsFromDatabase() {
        try {
            // Replace with your database connection details
            String jdbcUrl = "jdbc:mysql://localhost:3306/customer";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String query = "SELECT FoodName, Price, image_path FROM menuitems";
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Item> newItems = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("FoodName");
                double price = resultSet.getDouble("Price");
                String imagePath = resultSet.getString("image_path");
                newItems.add(new Item(name, price, imagePath, 1));
            }

            resultSet.close();
            statement.close();
            connection.close();

            setItems(newItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Item {
        private String name;
        private double price;
        private String imagePath;
        private int quantity;

        public Item(String name, double price, String imagePath, int quantity) {
            this.name = name;
            this.price = price;
            this.imagePath = imagePath;
            this.quantity = quantity;
        }

        // Getters and setters...
    }
}

