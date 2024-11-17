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

public class OrdersPlacedController {

    @FXML
    private ComboBox<String> orderNumberComboBox;

    @FXML
    private TextField orderTotalField;

    @FXML
    private ListView<String> storeOrderListView;

    @FXML
    private Button cancelOrderButton;

    @FXML
    private Button exportOrdersButton;

    @FXML
    public void initialize() {
        // Initialization logic if needed, such as populating the ComboBox
        populateOrderNumberComboBox();

        // Add a listener to update the order total when a different order is selected
        orderNumberComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateOrderTotal(newSelection);
                updateOrderPizzasListView(newSelection);
            }
        });
    }


    private void updateOrderTotal(String newSelection) {
        if (newSelection != null) {
            // Extract order number (assuming order number is displayed as "Order #<number>")
            String selectedOrderText = newSelection;
            int orderNumber = Integer.parseInt(selectedOrderText.replace("Order #", ""));

            // Retrieve the corresponding order from the order history
            OrderModel orderModel = OrderModel.getInstance();
            Order selectedOrder = orderModel.getOrderHistory().findOrderByNumber(orderNumber);

            if (selectedOrder != null) {
                // Display the order total (tax included)
                double totalCost = selectedOrder.getTotalCost();
                orderTotalField.setText(String.format("$%.2f", totalCost));
            }
        }
    }

    private void updateOrderPizzasListView(String newSelection) {
        if (newSelection != null) {
            // Extract order number (assuming order number is displayed as "Order #<number>")
            int orderNumber = Integer.parseInt(newSelection.replace("Order #", ""));

            // Retrieve the corresponding order from the order history
            OrderModel orderModel = OrderModel.getInstance();
            Order selectedOrder = orderModel.getOrderHistory().findOrderByNumber(orderNumber);

            if (selectedOrder != null) {
                // Populate the ListView with pizzas from the selected order
                ObservableList<String> pizzaDescriptions = FXCollections.observableArrayList();
                for (Pizza pizza : selectedOrder.getPizzas()) {
                    pizzaDescriptions.add(pizza.toString()); // Assuming Pizza has a meaningful toString() method
                }
                storeOrderListView.setItems(pizzaDescriptions);
            }
        }
    }

    private void populateOrderNumberComboBox() {
        OrderModel orderModel = OrderModel.getInstance(); // Assuming you have a singleton for OrderModel
        ObservableList<String> orderNumbers = FXCollections.observableArrayList();

        // Convert each order to a string representation (e.g., order number or a descriptive string)
        for (Order order : orderModel.getOrderHistory().getOrders()) {
            orderNumbers.add("Order #" + order.getNumber()); // Customize as needed
        }

        orderNumberComboBox.setItems(orderNumbers);
    }


    @FXML
    private void handleCancelOrderAction() {
        // Logic for canceling the selected order
        String selectedOrder = orderNumberComboBox.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            // Remove the order or handle it accordingly
            System.out.println("Canceling order: " + selectedOrder);
        }
    }

    @FXML
    private void handleExportOrdersAction() {
        // Logic to export store orders
        System.out.println("Exporting store orders...");
    }

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

        OrderModel orderModel = OrderModel.getInstance();
        Order selectedOrderObj = orderModel.getOrderHistory().findOrderByNumber(orderNumber);

        if (selectedOrderObj != null) {
            // Remove the order from the order history
            boolean removed = orderModel.getOrderHistory().removeOrder(selectedOrderObj);

            if (removed) {
                // Update the UI components if the order was successfully removed
                populateOrderNumberComboBox(); // Refresh the ComboBox to reflect the change
                storeOrderListView.getItems().clear(); // Clear the ListView
                orderTotalField.clear(); // Clear the total field


                // Optionally, show a confirmation message
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

    @FXML
    private void handleExportOrdersButtonAction() {
        // Logic to export store orders
        // Prompt the user to select a file location to save the export
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Store Orders");
        fileChooser.setInitialFileName("store_orders.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(null);

        System.out.println("Exporting store orders...");

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Retrieve all orders from the order history
                OrderModel orderModel = OrderModel.getInstance();
                for (Order order : orderModel.getOrderHistory().getOrders()) {
                    writer.write(order.toString());
                    writer.newLine(); // Add a newline between orders
                    writer.newLine(); // Optional: add an extra newline for better separation
                }

                // Show a success message
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
