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
 * Controller class for managing New York-style pizza orders in the UI.
 * Handles user interactions, manages available and selected toppings, updates pizza details, and processes orders.
 * Provides functionalities specific to New York-style pizzas.
 * This class integrates with JavaFX components for UI interaction.
 * Author: Belwin Julian, Suhas Murthy
 */
public class NewYorkController {

    private Pizza currentPizza; // Reference to the currently selected Pizza object
    private static final int MAX_TOPPINGS = 7; // Maximum number of toppings allowed for a pizza
    private final NYPizza pizzaFactory = new NYPizza(); // Factory instance for creating New York-style pizzas
    private OrderModel orderModel = OrderModel.getInstance(); // Singleton instance for managing orders

    @FXML
    public ToggleGroup sizeGroup; // ToggleGroup for selecting pizza size
    @FXML
    private ComboBox<String> buildYourOwnComboBox; // ComboBox for selecting pizza type

    @FXML
    private TextField crustTextField; // TextField for displaying crust type

    @FXML
    private ListView<String> availableToppingsListView; // ListView for displaying available toppings

    @FXML
    private ListView<String> selectedToppingsListView; // ListView for displaying selected toppings

    @FXML
    private ImageView pizzaImageView; // ImageView for displaying the pizza image

    @FXML
    private TextField priceTextField; // TextField for displaying the current price of the pizza

    private ObservableList<String> availableToppings = FXCollections.observableArrayList(); // List of available toppings

    /**
     * Initializes the controller, setting up default values and event listeners for the UI components.
     */
    @FXML
    public void initialize() {
        // Populate available toppings list
        for (Topping topping : Topping.values()) {
            availableToppings.add(topping.name()); // Add string representation of each topping
        }
        availableToppingsListView.setItems(availableToppings);

        // Set default ComboBox value and initial crust/image updates
        crustTextField.setEditable(false);
        buildYourOwnComboBox.getItems().addAll("Build Your Own", "Meatzza", "Deluxe", "BBQChicken");
        buildYourOwnComboBox.setValue("Build Your Own");
        updateImage("Build Your Own");
        updateCrust("Build Your Own");
        createPizza("Build Your Own");

        // Set up event listener for ComboBox selection
        buildYourOwnComboBox.setOnAction(e -> {
            String selectedPizzaType = buildYourOwnComboBox.getValue();
            updateCrust(selectedPizzaType);
            updateImage(selectedPizzaType);
            createPizza(selectedPizzaType);
            handleToppingModificationAvailability(); // Enable/disable topping modification based on pizza type
        });

        // Listener for size selection
        sizeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updatePizzaSize();
            }
        });
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
            case "BBQChicken":
                currentPizza = pizzaFactory.createBBQChicken(getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            case "Build Your Own":
                currentPizza = pizzaFactory.createBuildYourOwn(getCurrentSize());
                resetBuildYourOwnToppings();
                break;
            default:
                currentPizza = pizzaFactory.createBuildYourOwn(getCurrentSize());
                resetBuildYourOwnToppings();
        }
        updatePrice(); // Update the displayed price
    }

    /**
     * Sets fixed toppings for predefined pizzas and disables topping modification.
     * @param pizza the pizza object to set fixed toppings for
     */
    private void setFixedToppings(Pizza pizza) {
        selectedToppingsListView.getItems().clear();
        for (Topping topping : pizza.getToppings()) {
            selectedToppingsListView.getItems().add(topping.name());
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
     * Enables or disables the topping modification UI elements.
     * @param enable true to enable, false to disable
     */
    private void enableToppingModification(boolean enable) {
        availableToppingsListView.setDisable(!enable);
        selectedToppingsListView.setDisable(!enable);
    }

    /**
     * Updates the displayed pizza image based on the selected type.
     * @param pizzaType the type of pizza selected
     */
    private void updateImage(String pizzaType) {
        String imagePath;
        switch (pizzaType) {
            case "Meatzza":
                imagePath = "/MeatzzaNewYork.jpg"; // Path to Meatzza image for New York
                break;
            case "Deluxe":
                imagePath = "/DeluxeNewYork.jpg"; // Path to Deluxe image for New York
                break;
            case "BBQChicken":
                imagePath = "/BBQNewYork.jpg"; // Path to BBQ Chicken image for New York
                break;
            case "Build Your Own":
                imagePath = "/BuildYourOwnNewYork.jpg"; // Path to Build Your Own image for New York
                break;
            default:
                imagePath = "/BuildYourOwnNewYork.jpg"; // Fallback image
        }
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        pizzaImageView.setImage(image); // Set the image
    }

    /**
     * Updates the size of the current pizza and adjusts the displayed price.
     */
    private void updatePizzaSize() {
        if (currentPizza != null) {
            currentPizza.setSize(getCurrentSize());
            updatePrice();
        }
    }

    /**
     * Retrieves the currently selected pizza size from the ToggleGroup.
     * @return the selected Size enum value, or SMALL by default
     */
    private Size getCurrentSize() {
        RadioButton selectedSize = (RadioButton) sizeGroup.getSelectedToggle();
        if (selectedSize != null) {
            return Size.valueOf(selectedSize.getText().toUpperCase());
        }
        return Size.SMALL; // Default size if none selected
    }

    /**
     * Updates the displayed crust type based on the selected pizza type.
     * @param pizzaType the type of pizza selected
     */
    private void updateCrust(String pizzaType) {
        Crust crust;
        switch (pizzaType) {
            case "Meatzza":
                crust = Crust.HAND_TOSSED;
                break;
            case "Deluxe":
                crust = Crust.BROOKLYN;
                break;
            case "BBQChicken":
                crust = Crust.THIN;
                break;
            case "Build Your Own":
                crust = Crust.HAND_TOSSED;
                break;
            default:
                crust = Crust.HAND_TOSSED;
        }
        crustTextField.setText(crust.name()); // Update crust display
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
     * Resets the topping lists for Build Your Own pizzas.
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
     * Shows an alert if the maximum number of toppings is reached.
     */
    @FXML
    private void moveToSelected() {
        if (currentPizza.getToppings().size() >= MAX_TOPPINGS) {
            showMaxToppingsAlert();
            return;
        }
        String selectedTopping = availableToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            availableToppingsListView.getItems().remove(selectedTopping);
            selectedToppingsListView.getItems().add(selectedTopping);
            currentPizza.addTopping(Topping.valueOf(selectedTopping));
            updatePrice(); // Update price after topping modification
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
            selectedToppingsListView.getItems().remove(selectedTopping);
            availableToppingsListView.getItems().add(selectedTopping);
            currentPizza.removeTopping(Topping.valueOf(selectedTopping));
            updatePrice(); // Update price after topping modification
        }
    }

    /**
     * Adds the current pizza to the order and displays a confirmation message.
     * Shows a warning if no pizza is selected or created.
     */
    @FXML
    private void addToOrder() {
        if (currentPizza == null) {
            // Alert user if no pizza is selected or created
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Pizza Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a pizza before adding to the order.");
            alert.showAndWait();
            return;
        }
        // Add the current pizza to the order
        orderModel.getCurrentOrder().addPizza(currentPizza);
        // Show confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pizza Added");
        alert.setHeaderText(null);
        alert.setContentText("The pizza has been added to the order.");
        alert.showAndWait();
    }
}
