package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Order;
import model.OrderModel;
import model.Pizza;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller class for managing and displaying orders placed in the store.
 * Handles actions such as viewing orders, canceling orders, and exporting orders to a file.
 * This class integrates with JavaFX components to enable user interaction with store orders.
 * Author: Belwin Julian, Suhas Murthy
 */
public class OrdersPlacedController {

    @FXML
    private ComboBox<String> orderNumberComboBox; // ComboBox for selecting an order by number

    @FXML
    private TextField orderTotalField; // TextField to display the total cost of the selected order

    @FXML
    private ListView<String> storeOrderListView; // ListView to display the pizzas in the selected order

    @FXML
    private Button cancelOrderButton; // Button for canceling the selected order

    @FXML
    private Button exportOrdersButton; // Button for exporting store orders to a file

    /**
     * Initializes the controller and sets up default values and event listeners for UI components.
     */
    @FXML
    public void initialize() {
        populateOrderNumberComboBox(); // Populate the ComboBox with order numbers

        // Listener to update order details when a different order is selected
        orderNumberComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateOrderTotal(newSelection); // Update the displayed order total
                updateOrderPizzasListView(newSelection); // Update the ListView with the selected order's pizzas
            }
        });
    }

    /**
     * Updates the displayed order total when a new order is selected.
     * @param newSelection the selected order number string
     */
    private void updateOrderTotal(String newSelection) {
        if (newSelection != null) {
            // Extract order number from string (assuming format "Order #<number>")
            String selectedOrderText = newSelection;
            int orderNumber = Integer.parseInt(selectedOrderText.replace("Order #", ""));

            // Retrieve the corresponding order from order history
            OrderModel orderModel = OrderModel.getInstance();
            Order selectedOrder = orderModel.getOrderHistory().findOrderByNumber(orderNumber);

            if (selectedOrder != null) {
                // Display the total cost (including tax) of the order
                double totalCost = selectedOrder.getTotalCost();
                orderTotalField.setText(String.format("$%.2f", totalCost));
            }
        }
    }

    /**
     * Updates the ListView to display the pizzas in the selected order.
     * @param newSelection the selected order number string
     */
    private void updateOrderPizzasListView(String newSelection) {
        if (newSelection != null) {
            // Extract order number from string (assuming format "Order #<number>")
            int orderNumber = Integer.parseInt(newSelection.replace("Order #", ""));

            // Retrieve the corresponding order from order history
            OrderModel orderModel = OrderModel.getInstance();
            Order selectedOrder = orderModel.getOrderHistory().findOrderByNumber(orderNumber);

            if (selectedOrder != null) {
                // Populate the ListView with descriptions of pizzas in the order
                ObservableList<String> pizzaDescriptions = FXCollections.observableArrayList();
                for (Pizza pizza : selectedOrder.getPizzas()) {
                    pizzaDescriptions.add(pizza.toString()); // Assumes Pizza class has a meaningful toString() method
                }
                storeOrderListView.setItems(pizzaDescriptions);
            }
        }
    }

    /**
     * Populates the ComboBox with order numbers from the order history.
     */
    private void populateOrderNumberComboBox() {
        OrderModel orderModel = OrderModel.getInstance(); // Get the singleton instance of OrderModel
        ObservableList<String> orderNumbers = FXCollections.observableArrayList();

        // Convert each order to a string representation (e.g., "Order #<number>")
        for (Order order : orderModel.getOrderHistory().getOrders()) {
            orderNumbers.add("Order #" + order.getNumber()); // Customize as needed
        }
        orderNumberComboBox.setItems(orderNumbers); // Set the ComboBox items
    }

    /**
     * Handles the action of canceling the selected order.
     * Displays a confirmation or error message based on the outcome.
     */
    @FXML
    private void handleCancelOrderButtonAction() {
        // Get the selected order number from the ComboBox
        String selectedOrder = orderNumberComboBox.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            // Show a warning if no order is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Order Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order to cancel.");
            alert.showAndWait();
            return;
        }

        int orderNumber = Integer.parseInt(selectedOrder.replace("Order #", ""));

        // Retrieve the corresponding order from order history
        OrderModel orderModel = OrderModel.getInstance();
        Order selectedOrderObj = orderModel.getOrderHistory().findOrderByNumber(orderNumber);

        if (selectedOrderObj != null) {
            // Remove the order from the order history
            boolean removed = orderModel.getOrderHistory().removeOrder(selectedOrderObj);

            if (removed) {
                // Update the UI components after successful removal
                populateOrderNumberComboBox(); // Refresh the ComboBox
                storeOrderListView.getItems().clear(); // Clear the ListView
                orderTotalField.clear(); // Clear the total field

                // Show a confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Canceled");
                alert.setHeaderText(null);
                alert.setContentText("Order #" + orderNumber + " has been canceled.");
                alert.showAndWait();
            } else {
                // Show an error message if the order could not be removed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while trying to cancel the order.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Handles the action of exporting store orders to a text file.
     * Allows the user to select a file location for saving the export.
     */
    @FXML
    private void handleExportOrdersButtonAction() {
        // Prompt the user to select a file location to save the export
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Store Orders");
        fileChooser.setInitialFileName("store_orders.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Retrieve all orders from the order history
                OrderModel orderModel = OrderModel.getInstance();
                for (Order order : orderModel.getOrderHistory().getOrders()) {
                    writer.write(order.toString()); // Write the order details to the file
                    writer.newLine(); // Add a newline between orders
                    writer.newLine(); // Optional: add extra newline for better separation
                }

                // Show a success message upon successful export
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("Store orders have been successfully exported to " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                // Show an error message if the file could not be written
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Failed");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while exporting store orders.");
                alert.showAndWait();
            }
        }
    }
}
