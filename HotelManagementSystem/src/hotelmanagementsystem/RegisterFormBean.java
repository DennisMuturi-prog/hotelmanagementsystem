/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelmanagementsystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * This class represents a registration form as a JavaBean.
 */
public class RegisterFormBean {

    private String username;
    private String password;
    private String confirmPassword;

    // PropertyChangeSupport for handling property change events
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public RegisterFormBean() {
        // Default constructor
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        String oldUsername = this.username;
        this.username = username;
        propertyChangeSupport.firePropertyChange("username", oldUsername, username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        propertyChangeSupport.firePropertyChange("password", oldPassword, password);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        String oldConfirmPassword = this.confirmPassword;
        this.confirmPassword = confirmPassword;
        propertyChangeSupport.firePropertyChange("confirmPassword", oldConfirmPassword, confirmPassword);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void register() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false", "root", "");
            Statement stm = con.createStatement();
            String sql = "insert into restaurantcustomers(Customername,password,priviledge) values('" + username + "','" + password + "','Customer')";
            stm.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        // Create an instance of the RegisterFormBean
        RegisterFormBean registerFormBean = new RegisterFormBean();

        // Set the username, password, and confirm password
        registerFormBean.setUsername("exampleUser");
        registerFormBean.setPassword("examplePassword");
        registerFormBean.setConfirmPassword("examplePassword");

        // Perform registration
        registerFormBean.register();

        // Print a message indicating successful registration
        System.out.println("Registration successful");
    }
}

