package com.mycompany.grocerymanagerapp;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

public class PrimaryController implements Initializable {
    private GroceryManagerDAO dao;
    private int additionalAmount;
    private String upcCode;
    private ResultSet lowStockItems;
    @FXML private TextArea display;
    
    
    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void updateInventory(ActionEvent event)  {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Update Inventory");
        dialog.setHeaderText(null);
        dialog.setContentText("UPC:");
        Optional<String> result1 = dialog.showAndWait();
        dialog.setContentText("Additional Amount:");
        Optional<String> result2 = dialog.showAndWait();
        
        if(result1.isPresent() && result2.isPresent()) {
            upcCode = result1.get();
            additionalAmount = Integer.parseInt(result2.get());
        }
        dao.updateItemAmount(upcCode, additionalAmount);
    }
    
    @FXML
    private void generateReport(ActionEvent event) throws SQLException, ClassNotFoundException {
        display.clear();
        lowStockItems = dao.lowStockItems();
        /** You had
        if (lowStockItems.next() == false){
            display.setText("None of the items in the inventory are low in stock");
        }
        else{
        while (lowStockItems.next()) {
            String upc = lowStockItems.getString(1);
            String amount = String.valueOf(lowStockItems.getInt(2));
            String item = lowStockItems.getString(3);
            addLine(upc,amount,item);
        }
        }
        * This contains a logic flaw. Since your code calls next() on the ResultSet 
        * twice before reading the first data from a row, you will always end up 
        * skipping a row. I fixed this for you. **/
        if (lowStockItems.next() == false){
            display.setText("None of the items in the inventory are low in stock");
        }
        else{
            String upc = lowStockItems.getString(1);
            String amount = String.valueOf(lowStockItems.getInt(2));
            String item = lowStockItems.getString(3);
            display.setText(upc + " " + amount + " " + item);
        }
        while (lowStockItems.next()) {
            String upc = lowStockItems.getString(1);
            String amount = String.valueOf(lowStockItems.getInt(2));
            String item = lowStockItems.getString(3);
            addLine(upc,amount,item);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new GroceryManagerDAO();
        display.setEditable(false);
    } 
    
    private void addLine(String upc, String amount, String item ) {
        if (display == null)
            display.setText(upc + " " + amount + " " + item);
        else
            display.setText(display.getText() + "\n" + upc + " " + amount + " " + item);
    }
    
}

/** One small error here - see the comment above for details.
 * 
 *  All other programs are correct.
 * 
 *  Your grade for this assignment is 79/80.
 */