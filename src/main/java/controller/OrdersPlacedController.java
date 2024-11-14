package controller;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
}
