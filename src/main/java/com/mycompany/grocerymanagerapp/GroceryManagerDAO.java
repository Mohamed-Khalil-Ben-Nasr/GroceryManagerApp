package com.mycompany.grocerymanagerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroceryManagerDAO {
    private Statement statement;
    private PreparedStatement updateStockStatement;
    private ResultSet resultSet;
    
    
    public GroceryManagerDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
              ("jdbc:mysql://localhost:3306/grocery?user=student&password=Cmsc250!");
            statement = connection.createStatement();
            
            //create update stock statement
            String updateStockSQL = "update inventory set amount = amount + ? where upc = ?";
            updateStockStatement = connection.prepareStatement(updateStockSQL);           
        } catch(Exception ex) {
            System.out.println("Problem opening database connection.");
            ex.printStackTrace();
        }
    }
    
    public void updateItemAmount(String code, int additionalAmount) {
        try{
            updateStockStatement.setInt(1, additionalAmount);
            updateStockStatement.setString(2, code);
            updateStockStatement.execute(); 
        } catch (Exception ex) {
            System.out.println("Error in Updating Amount of Selected Item");
            ex.printStackTrace();
        }
    }
    
    public ResultSet lowStockItems() throws SQLException, ClassNotFoundException{
        resultSet = statement.executeQuery
        ("select upc, amount, item from inventory_detail where amount<6");
        return resultSet;
    }
}
