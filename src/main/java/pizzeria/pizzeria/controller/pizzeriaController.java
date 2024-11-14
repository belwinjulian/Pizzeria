package pizzeria.pizzeria.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class pizzeriaController {

    @FXML
    private VBox ordersBox;
    @FXML
    private VBox chicagoVBox;
    @FXML
    private VBox nyVBox;
    @FXML
    private VBox currentVBox;

    @FXML
    private ImageView chicagoImage;

    @FXML
    private ImageView nyImage;

    @FXML
    private ImageView ordersImage;

    @FXML
    private ImageView currentOrderImage;

    public void initialize() {
        addHoverEffect(ordersBox);
        addHoverEffect(chicagoVBox);
        addHoverEffect(nyVBox);
        addHoverEffect(currentVBox);
    }

    private void addHoverEffect(VBox vbox) {
        vbox.setOnMouseEntered(e -> {
            vbox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 5;"); // Change the background color or add other styles as needed
        });
        vbox.setOnMouseExited(e -> {
            vbox.setStyle(""); // Reset to the default style when the mouse exits
        });
    }

    @FXML
    private void handleChicagoClick(MouseEvent event) {
        try {
            // Load the new FXML file for the Chicago view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chicago-menu.fxml")); // Adjust the path if needed
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

    @FXML
    private void handleNewYorkClick(MouseEvent event) {
        try {
            // Load the new FXML file for the Chicago view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newYork-menu.fxml")); // Adjust the path if needed
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

    @FXML
    private void handleCurrentOrderClick(MouseEvent event){
        try {
            // Load the new FXML file for the Chicago view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/currentOrder-menu.fxml")); // Adjust the path if needed
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

    @FXML
    private void handlePlacedOrdersClick(MouseEvent event){
        try {
            // Load the new FXML file for the Chicago view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ordersPlaced-menu.fxml")); // Adjust the path if needed
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
}
