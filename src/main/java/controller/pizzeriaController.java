package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Order;
import model.OrderHistory;

import java.io.IOException;

/**
 * Controller class for the main interface of the Pizzeria application.
 * Handles navigation to various pizza ordering views and provides visual feedback on mouse hover.
 * This controller also maintains instances of the current order and order history.
 * Author: Belwin Julian, Suhas Murthy
 */
public class pizzeriaController {

    private Order currentOrder = new Order(); // Shared instance of the current order across the application
    private OrderHistory orderHistory = new OrderHistory(); // Shared instance for managing all orders

    @FXML
    private VBox ordersBox; // VBox element for orders section
    @FXML
    private VBox chicagoVBox; // VBox element for Chicago-style pizza section
    @FXML
    private VBox nyVBox; // VBox element for New York-style pizza section
    @FXML
    private VBox currentVBox; // VBox element for current orders section

    @FXML
    private ImageView chicagoImage; // ImageView for Chicago-style pizza icon
    @FXML
    private ImageView nyImage; // ImageView for New York-style pizza icon
    @FXML
    private ImageView ordersImage; // ImageView for orders icon
    @FXML
    private ImageView currentOrderImage; // ImageView for current order icon

    /**
     * Initializes the controller and sets up hover effects for UI elements.
     */
    public void initialize() {
        addHoverEffect(ordersBox);
        addHoverEffect(chicagoVBox);
        addHoverEffect(nyVBox);
        addHoverEffect(currentVBox);
    }

    /**
     * Adds a hover effect to a VBox element by changing its style on mouse enter and exit.
     * @param vbox the VBox element to add the hover effect to
     */
    private void addHoverEffect(VBox vbox) {
        vbox.setOnMouseEntered(e -> {
            vbox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 5;"); // Change background color on hover
        });
        vbox.setOnMouseExited(e -> {
            vbox.setStyle(""); // Reset to default style when mouse exits
        });
    }

    /**
     * Getter for the current order.
     * @return the current Order instance
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Getter for the order history.
     * @return the OrderHistory instance
     */
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    /**
     * Handles the click event for the Chicago-style pizza view.
     * Opens a new window displaying the Chicago-style pizza menu.
     * @param event the MouseEvent triggered by clicking on the Chicago section
     */
    @FXML
    private void handleChicagoClick(MouseEvent event) {
        try {
            // Load the new FXML file for the Chicago view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chicago-menu.fxml")); // Adjust the path if necessary
            Parent chicagoView = loader.load();

            // Create a new Stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("Chicago Style Pizza");
            newStage.setScene(new Scene(chicagoView));

            // Show the new window
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event for the New York-style pizza view.
     * Opens a new window displaying the New York-style pizza menu.
     * @param event the MouseEvent triggered by clicking on the New York section
     */
    @FXML
    private void handleNewYorkClick(MouseEvent event) {
        try {
            // Load the new FXML file for the New York view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newYork-menu.fxml")); // Adjust the path if necessary
            Parent newYorkView = loader.load();

            // Create a new Stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("New York Style Pizza");
            newStage.setScene(new Scene(newYorkView));

            // Show the new window
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event for the current orders view.
     * Opens a new window displaying the current orders.
     * @param event the MouseEvent triggered by clicking on the current orders section
     */
    @FXML
    private void handleCurrentOrderClick(MouseEvent event) {
        try {
            // Load the new FXML file for the current orders view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/currentOrder-menu.fxml")); // Adjust the path if necessary
            Parent currentOrderView = loader.load();

            // Create a new Stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("Current Order");
            newStage.setScene(new Scene(currentOrderView));

            // Show the new window
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event for the placed orders view.
     * Opens a new window displaying all placed orders.
     * @param event the MouseEvent triggered by clicking on the placed orders section
     */
    @FXML
    private void handlePlacedOrdersClick(MouseEvent event) {
        try {
            // Load the new FXML file for the placed orders view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ordersPlaced-menu.fxml")); // Adjust the path if necessary
            Parent placedOrdersView = loader.load();

            // Create a new Stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("Placed Orders");
            newStage.setScene(new Scene(placedOrdersView));

            // Show the new window
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
