package controller; // Replace with your actual package

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CurrentOrderController {

    @FXML
    private TextField orderNumberField;

    @FXML
    private ListView<String> orderListView;

    @FXML
    private TextField subtotalField;

    @FXML
    private TextField salesTaxField;

    @FXML
    private TextField totalField;

    @FXML
    private Button removePizzaButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button clearOrderButton;

    // Initialize method (optional, if you need to set up initial state)
    @FXML
    public void initialize() {
        // You can perform any necessary initialization here
        subtotalField.setText("0.00");
        salesTaxField.setText("0.00");
        totalField.setText("0.00");

    }

    // Event handler for removing pizza
    @FXML
    private void handleRemovePizzaButtonAction() {
        // Implement logic to remove selected pizza from the ListView
        String selectedPizza = orderListView.getSelectionModel().getSelectedItem();
        if (selectedPizza != null) {
            orderListView.getItems().remove(selectedPizza);
            // Update subtotal, sales tax, and total fields accordingly
        }
    }

    // Event handler for placing the order
    @FXML
    private void handlePlaceOrderButtonAction() {
        // Implement logic to place the order
        System.out.println("Order placed.");
        // Clear the current order after placing
        orderListView.getItems().clear();
        subtotalField.setText("0.00");
        salesTaxField.setText("0.00");
        totalField.setText("0.00");
    }

    // Event handler for clearing the order
    @FXML
    private void handleClearOrderButtonAction() {
        // Clear all items in the ListView
        orderListView.getItems().clear();
        subtotalField.setText("0.00");
        salesTaxField.setText("0.00");
        totalField.setText("0.00");
    }
}
