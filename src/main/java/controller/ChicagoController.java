package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

/**
 * Controller class for managing the UI interactions for Chicago-style pizza orders.
 * Handles user input, displays available and selected toppings, and updates pizza details.
 * @author Belwin Julian, Suhas Murthy
 */
public class ChicagoController {

    private Pizza currentPizza; // Reference to the current Pizza object
    private static final int MAX_TOPPINGS = 7; // Maximum number of toppings allowed
    private final ChicagoPizza pizzaFactory = new ChicagoPizza(); // Factory instance for creating Chicago-style pizzas
    private OrderModel orderModel = OrderModel.getInstance(); // Singleton instance for managing orders

    @FXML
    public ToggleGroup sizeGroup; // Toggle group for selecting pizza size
    @FXML
    private ComboBox<String> buildYourOwnComboBox; // ComboBox for selecting pizza type
    @FXML
    private TextField crustTextField; // TextField to display the crust type
    @FXML
    private ListView<String> availableToppingsListView; // ListView for displaying available toppings
    @FXML
    private ListView<String> selectedToppingsListView; // ListView for displaying selected toppings
    @FXML
    private ImageView pizzaImageView; // ImageView to display the selected pizza image
    @FXML
    private TextField priceTextField; // TextField to display the current price of the pizza
    @FXML
    private Button moveToSelectedButton; // Button to move toppings to selected list
    @FXML
    private Button moveToAvailableButton; // Button to move toppings to available list

    // List of available toppings
    private ObservableList<String> availableToppings = FXCollections.observableArrayList();

    /**
     * Initializes the controller and sets up initial data and listeners for UI components.
     */
    @FXML
    public void initialize() {
        // Populate available toppings list
        for (Topping topping : Topping.values()) {
            availableToppings.add(topping.name()); // Adds each topping's string representation
        }
        availableToppingsListView.setItems(availableToppings);

        // Set crust text field as non-editable
        crustTextField.setEditable(false);

        // Set default ComboBox value and update image
        buildYourOwnComboBox.setValue("Build Your Own");
        updateImage("Build Your Own");

        // Create default pizza and update crust
        createPizza("Build Your Own");
        updateCrust("Build Your Own");

        // Set up listeners for user input
        buildYourOwnComboBox.setOnAction(e -> {
            updateCrust(buildYourOwnComboBox.getValue());
            updateImage(buildYourOwnComboBox.getValue());
            createPizza(buildYourOwnComboBox.getValue());
            handleToppingModificationAvailability();
        });

        sizeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updatePizzaSize();
            }
        });
    }

    /**
     * Sets fixed toppings for non-build-your-own pizzas and updates the UI accordingly.
     * @param pizza the pizza object to set fixed toppings for
     */
    private void setFixedToppings(Pizza pizza) {
        selectedToppingsListView.getItems().clear(); // Clear previous selections
        for (Topping topping : pizza.getToppings()) {
            selectedToppingsListView.getItems().add(topping.name()); // Display toppings
        }
        enableToppingModification(false); // Disable topping modification for predefined pizzas
    }

    /**
     * Determines if topping modification should be enabled based on the current pizza type.
     */
    private void handleToppingModificationAvailability() {
        enableToppingModification(currentPizza instanceof BuildYourOwn);
    }

    /**
     * Enables or disables the UI elements for modifying toppings.
     * @param enable true to enable, false to disable
     */
    private void enableToppingModification(boolean enable) {
        moveToSelectedButton.setDisable(!enable);
        moveToAvailableButton.setDisable(!enable);
        availableToppingsListView.setDisable(!enable);
    }

    /**
     * Updates the displayed pizza image based on the selected type.
     * @param pizzaType the type of pizza selected
     */
    private void updateImage(String pizzaType) {
        String imagePath;
        switch (pizzaType) {
            case "Meatzza":
                imagePath = "/MeatzzaChicago.jpg"; // Path to Meatzza image
                break;
            case "Deluxe":
                imagePath = "/DeluxeChicago.jpg"; // Path to Deluxe image
                break;
            case "BBQChicken":
                imagePath = "/BBQChicago.jpg"; // Path to BBQ Chicken image
                break;
            case "Build Your Own":
                imagePath = "/Chicago-Style-pizza.jpg"; // Path to Build Your Own image
                break;
            default:
                imagePath = "/Chicago-Style-pizza.jpg"; // Default fallback image
        }
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        pizzaImageView.setImage(image); // Set the image
    }

    /**
     * Creates a new pizza object based on the selected type.
     * @param type the type of pizza to create
     */
    private void createPizza(String type) {
        switch (type) {
            case "Meatzza":
                currentPizza = pizzaFactory.createMeatzza(getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            case "Deluxe":
                currentPizza = pizzaFactory.createDeluxe(getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            case "Build Your Own":
                currentPizza = pizzaFactory.createBuildYourOwn(getCurrentSize());
                resetBuildYourOwnToppings();
                break;
            case "BBQChicken":
                currentPizza = pizzaFactory.createBBQChicken(getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            default:
                currentPizza = pizzaFactory.createBuildYourOwn(getCurrentSize());
                resetBuildYourOwnToppings();
        }
        updatePrice();
    }

    /**
     * Updates the displayed price of the current pizza.
     */
    private void updatePrice() {
        if (currentPizza != null) {
            double price = currentPizza.price();
            priceTextField.setText(String.format("$%.2f", price));
        }
    }

    /**
     * Retrieves the currently selected pizza size.
     * @return the selected size, or SMALL by default
     */
    private Size getCurrentSize() {
        RadioButton selectedSize = (RadioButton) sizeGroup.getSelectedToggle();
        if (selectedSize != null) {
            return Size.valueOf(selectedSize.getText().toUpperCase());
        }
        return Size.SMALL; // Default size
    }

    /**
     * Updates the crust displayed based on the selected pizza type.
     * @param pizzaType the type of pizza selected
     */
    private void updateCrust(String pizzaType) {
        Crust crust;
        switch (pizzaType) {
            case "Meatzza":
                crust = Crust.STUFFED;
                break;
            case "Deluxe":
                crust = Crust.PAN;
                break;
            case "BBQChicken":
                crust = Crust.HAND_TOSSED;
                break;
            case "Build Your Own":
                crust = Crust.PAN;
                break;
            default:
                crust = Crust.PAN;
        }
        crustTextField.setText(crust.name()); // Update crust display
    }

    /**
     * Updates the size of the current pizza and adjusts the price.
     */
    private void updatePizzaSize() {
        if (currentPizza != null) {
            currentPizza.setSize(getCurrentSize());
            updatePrice();
        }
    }

    /**
     * Resets the toppings list for Build Your Own pizzas.
     */
    private void resetBuildYourOwnToppings() {
        selectedToppingsListView.getItems().clear();
        availableToppingsListView.getItems().clear();
        availableToppingsListView.getItems().addAll(FXCollections.observableArrayList(Topping.values()).stream()
                .map(Topping::name)
                .toList());
        if (currentPizza instanceof BuildYourOwn) {
            currentPizza.getToppings().clear();
        }
    }

    /**
     * Moves a topping from the available list to the selected list.
     */
    @FXML
    private void moveToSelected() {
        if (currentPizza.getToppings().size() >= MAX_TOPPINGS) {
            showMaxToppingsAlert(); // Alert user if maximum toppings reached
            return;
        }
        String selectedTopping = availableToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            availableToppingsListView.getItems().remove(selectedTopping);
            selectedToppingsListView.getItems().add(selectedTopping);
            currentPizza.addTopping(Topping.valueOf(selectedTopping));
            updatePrice(); // Update price after adding a topping
        }
    }

    /**
     * Displays an alert when the maximum number of toppings is reached.
     */
    private void showMaxToppingsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Maximum Toppings Reached");
        alert.setHeaderText(null);
        alert.setContentText("You can only add up to " + MAX_TOPPINGS + " toppings.");
        alert.showAndWait();
    }

    /**
     * Moves a topping from the selected list back to the available list.
     */
    @FXML
    private void moveToAvailable() {
        String selectedTopping = selectedToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            selectedToppingsListView.getItems().remove(selectedTopping); // Remove from selected list
            availableToppingsListView.getItems().add(selectedTopping); // Add back to available list
            currentPizza.removeTopping(Topping.valueOf(selectedTopping)); // Remove topping from pizza
            updatePrice(); // Update the displayed price
        }
    }

    /**
     * Adds the current pizza to the order and displays a confirmation message.
     */
    @FXML
    private void addToOrder() {
        if (currentPizza == null) {
            // Alert the user if no pizza is selected or created
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Pizza Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a pizza before adding to the order.");
            alert.showAndWait();
            return;
        }
        orderModel.getCurrentOrder().addPizza(currentPizza); // Add the pizza to the order
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pizza Added");
        alert.setHeaderText(null);
        alert.setContentText("The pizza has been added to the order.");
        alert.showAndWait(); // Show confirmation
    }
}

