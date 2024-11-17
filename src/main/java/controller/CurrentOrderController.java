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

/**
 * Controller class for managing the current pizza order interface.
 * Handles displaying, updating, and managing orders through user interactions.
 * Provides functionality for removing pizzas, placing an order, and clearing an order.
 * @author Belwin Julian, Suhas Murthy
 */
public class CurrentOrderController {

    @FXML
    private TextField orderNumberField; // Displays the current order number
    @FXML
    private ListView<String> orderListView; // ListView to display pizzas in the order
    @FXML
    private TextField subtotalField; // Displays the subtotal of the current order
    @FXML
    private TextField salesTaxField; // Displays the sales tax for the order
    @FXML
    private TextField totalField; // Displays the total cost of the order
    @FXML
    private Button removePizzaButton; // Button to remove a selected pizza
    @FXML
    private Button placeOrderButton; // Button to place the order
    @FXML
    private Button clearOrderButton; // Button to clear the order

    /**
     * Initializes the controller by setting up default values for fields and updating order information.
     */
    @FXML
    public void initialize() {
        // Initialize default values for text fields
        subtotalField.setText("0.00");
        salesTaxField.setText("0.00");
        totalField.setText("0.00");
        subtotalField.setEditable(false);
        salesTaxField.setEditable(false);
        totalField.setEditable(false);
        orderNumberField.setEditable(false);

        // Update order details
        updateOrderNumber();
        updatePizzaListView();
        updateOrderTotals();
    }

    /**
     * Updates the displayed order totals: subtotal, sales tax, and total cost.
     */
    private void updateOrderTotals() {
        OrderModel orderModel = OrderModel.getInstance();
        Order currentOrder = orderModel.getCurrentOrder();
        double subtotal = currentOrder.getTotalCost() - currentOrder.getSalesTax();
        double salesTax = currentOrder.getSalesTax();
        double total = currentOrder.getTotalCost();

        // Update text fields with formatted values
        subtotalField.setText(String.format("$%.2f", subtotal));
        salesTaxField.setText(String.format("$%.2f", salesTax));
        totalField.setText(String.format("$%.2f", total));
    }

    /**
     * Updates the displayed order number based on the current order.
     */
    private void updateOrderNumber() {
        OrderModel orderModel = OrderModel.getInstance();
        Order currentOrder = orderModel.getCurrentOrder();
        orderNumberField.setText(String.valueOf(currentOrder.getNumber()));
    }

    /**
     * Updates the ListView to display the pizzas in the current order.
     */
    private void updatePizzaListView() {
        OrderModel orderModel = OrderModel.getInstance();
        Order currentOrder = orderModel.getCurrentOrder();

        // Convert pizzas to a list of strings for display
        ObservableList<String> pizzaDescriptions = FXCollections.observableArrayList();
        for (Pizza pizza : currentOrder.getPizzas()) {
            pizzaDescriptions.add(pizza.toString()); // Assumes Pizza class has a meaningful toString() method
        }

        // Set items in the ListView
        orderListView.setItems(pizzaDescriptions);
    }

    /**
     * Event handler for removing a selected pizza from the order.
     * Displays an alert if no pizza is selected.
     */
    @FXML
    private void handleRemovePizzaButtonAction() {
        OrderModel orderModel = OrderModel.getInstance();
        Order currentOrder = orderModel.getCurrentOrder();

        // Get selected pizza from the ListView
        String selectedPizza = orderListView.getSelectionModel().getSelectedItem();
        if (selectedPizza != null) {
            Pizza pizzaToRemove = findPizzaByDescription(selectedPizza, currentOrder);
            currentOrder.removePizza(pizzaToRemove); // Remove pizza from order

            // Update order details
            updateOrderNumber();
            updatePizzaListView();
            updateOrderTotals();
        } else {
            // Show alert if no pizza is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Pizza Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a pizza to remove.");
            alert.showAndWait();
        }
    }

    /**
     * Finds a pizza object in the current order based on its description.
     * @param description the description string to match
     * @param currentOrder the current order containing pizzas
     * @return the matching Pizza object, or null if not found
     */
    private Pizza findPizzaByDescription(String description, Order currentOrder) {
        // Iterate through pizzas in the order to find a match
        for (Pizza pizza : currentOrder.getPizzas()) {
            if (pizza.toString().equals(description)) { // Assumes toString() matches displayed description
                return pizza;
            }
        }
        return null; // Return null if no match is found
    }

    /**
     * Event handler for placing the current order.
     * Displays a confirmation or warning based on the state of the order.
     */
    @FXML
    private void handlePlaceOrderButtonAction() {
        OrderModel orderModel = OrderModel.getInstance();
        if (orderModel.finalizeCurrentOrder()) {
            // Update order details after placing the order
            updateOrderNumber();
            updatePizzaListView();
            updateOrderTotals();

            // Display confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setHeaderText(null);
            alert.setContentText("Your order has been placed successfully!");
            alert.showAndWait();
        } else {
            // Display warning if the order is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Order");
            alert.setHeaderText(null);
            alert.setContentText("There are no pizzas in the current order. Please add at least one pizza before placing the order.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler for clearing the current order.
     * Removes all items and resets the order state.
     */
    @FXML
    private void handleClearOrderButtonAction() {
        OrderModel orderModel = OrderModel.getInstance();
        Order currentOrder = orderModel.getCurrentOrder();
        currentOrder.clearOrder(); // Clear all pizzas from the order

        // Update order details
        updateOrderNumber();
        updatePizzaListView();
        updateOrderTotals();
    }
}
