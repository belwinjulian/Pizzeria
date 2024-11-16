package controller; // Replace with your actual package

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Order;
import model.OrderModel;
import model.Pizza;

public class CurrentOrderController {

    OrderModel orderModel = OrderModel.getInstance();
    Order currentOrder = orderModel.getCurrentOrder();

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
        subtotalField.setEditable(false);
        salesTaxField.setEditable(false);
        totalField.setEditable(false);
        orderNumberField.setEditable(false);


        updateOrderNumber();
        updatePizzaListView();
        updateOrderTotals();


    }

    private void updateOrderTotals(){
        double subtotal = currentOrder.getTotalCost()-currentOrder.getSalesTax();
        double salesTax = currentOrder.getSalesTax();
        double total = currentOrder.getTotalCost();

        // Update the text fields
        subtotalField.setText(String.format("$%.2f", subtotal));
        salesTaxField.setText(String.format("$%.2f", salesTax));
        totalField.setText(String.format("$%.2f", total));
    }

    private void updateOrderNumber() {

        orderNumberField.setText(String.valueOf(currentOrder.getNumber()));
    }



    private void updatePizzaListView() {


        // Convert pizzas to a list of strings for display
        ObservableList<String> pizzaDescriptions = FXCollections.observableArrayList();
        for (Pizza pizza : currentOrder.getPizzas()) {
            pizzaDescriptions.add(pizza.toString()); // Assuming the Pizza class has a meaningful toString method
        }

        // Set the items in the ListView
        orderListView.setItems(pizzaDescriptions);
    }

    // Event handler for removing pizza
    @FXML
    private void handleRemovePizzaButtonAction() {
        // Implement logic to remove selected pizza from the ListView
        String selectedPizza = orderListView.getSelectionModel().getSelectedItem();
        if (selectedPizza != null) {
            Pizza pizzaToRemove = findPizzaByDescription(selectedPizza, currentOrder);
            currentOrder.removePizza(pizzaToRemove);

            updateOrderNumber();
            updatePizzaListView();
            updateOrderTotals();

            //orderListView.getItems().remove(selectedPizza);
            // Update subtotal, sales tax, and total fields accordingly
        }
         else {
            // Show an alert if no pizza is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Pizza Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a pizza to remove.");
            alert.showAndWait();
        }
    }

    private Pizza findPizzaByDescription(String description, Order currentOrder) {
        // This method should find a pizza object that matches the description string in the order
        for (Pizza pizza : currentOrder.getPizzas()) {
            if (pizza.toString().equals(description)) { // Assuming toString() provides the same description as displayed
                return pizza;
            }
        }
        return null;
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
