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

/**
 * This class represents a login page as a JavaBean.
 */
public class LoginBean {

    private String username;
    private String password;
    private String privilege;

    // PropertyChangeSupport for handling property change events
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public LoginBean() {
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

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        String oldPrivilege = this.privilege;
        this.privilege = privilege;
        propertyChangeSupport.firePropertyChange("privilege", oldPrivilege, privilege);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public String authenticate() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false", "root", "");
            Statement stm = con.createStatement();
            String sql = "select * from restaurantcustomers where Customername='" + username + "' and password='" + password + "'";
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                privilege = rs.getString("privilege");
                con.close();
            } else {
                privilege = "not found";
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return privilege;
    }
    public static void main(String args[]) {
        // Create an instance of the LoginPageBean
        LoginBean loginPageBean = new LoginBean();

        // Set the username and password
        loginPageBean.setUsername("exampleUser");
        loginPageBean.setPassword("examplePassword");

        // Perform authentication
        String loginStatus = loginPageBean.authenticate();

        // Print the login status
        System.out.println("Login Status: " + loginStatus);
    }

    
}

