package pizzeria.pizzeria.controller;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

public class ChicagoController {

    @FXML
    public ToggleGroup sizeGroup;
    @FXML
    private ComboBox<String> buildYourOwnComboBox;

    @FXML
    private TextField crustTextField;

    @FXML
    private ListView<String> availableToppingsListView;

    @FXML
    private ListView<String> selectedToppingsListView;

    @FXML
    private ImageView pizzaImageView;

    @FXML
    private TextField priceTextField;

    // Initialization method, called after all FXML fields are injected
    @FXML
    public void initialize() {
        // Any setup logic here, e.g., populate ComboBoxes or initialize event listeners
        // Example: Populate the buildYourOwnComboBox if necessary
        buildYourOwnComboBox.getItems().add("Custom Option");



    }

    @FXML
    private void moveToSelected() {
        // Logic for moving items from availableToppingsListView to selectedToppingsListView
        String selectedTopping = availableToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            availableToppingsListView.getItems().remove(selectedTopping);
            selectedToppingsListView.getItems().add(selectedTopping);
        }
    }

    @FXML
    private void moveToAvailable() {
        // Logic for moving items from selectedToppingsListView back to availableToppingsListView
        String selectedTopping = selectedToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            selectedToppingsListView.getItems().remove(selectedTopping);
            availableToppingsListView.getItems().add(selectedTopping);
        }
    }

    @FXML
    private void addToOrder() {
        // Logic for adding the order, e.g., collecting data and updating order list
        System.out.println("Order added with selected options");
    }
}

