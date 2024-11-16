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

public class ChicagoController {

    private Pizza currentPizza; // Reference to the current Pizza object
    private static final int MAX_TOPPINGS = 7; // Define the maximum number of toppings


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

    @FXML
    private Button moveToSelectedButton;

    @FXML
    private Button moveToAvailableButton;

    // Initialization method, called after all FXML fields are injected

    private ObservableList<String> availableToppings = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        for (Topping topping : Topping.values()) {
            availableToppings.add(topping.name()); // Adds the string representation of each topping
        }

        availableToppingsListView.setItems(availableToppings);

        crustTextField.setEditable(false);

        buildYourOwnComboBox.setValue("Build Your Own");

        updateImage("Build Your Own");

        buildYourOwnComboBox.setOnAction(e -> updateCrust(buildYourOwnComboBox.getValue()));

        buildYourOwnComboBox.setOnAction(e -> updateImage(buildYourOwnComboBox.getValue()));

        createPizza("Build Your Own");

        // Add listeners for changes
        buildYourOwnComboBox.setOnAction(e -> {
            updateCrust(buildYourOwnComboBox.getValue());
            updateImage(buildYourOwnComboBox.getValue());
            createPizza(buildYourOwnComboBox.getValue());
            handleToppingModificationAvailability(); // Check if topping modification should be allowed
        });

        sizeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updatePizzaSize();
            }
        });


    }
    private void setFixedToppings(Pizza pizza) {
        // Clear the selected toppings list
        selectedToppingsListView.getItems().clear();

        // Retrieve toppings from the currentPizza instance and display them
        for (Topping topping : pizza.getToppings()) {
            selectedToppingsListView.getItems().add(topping.name());
        }

        // Disable topping modification for predefined pizzas
        enableToppingModification(false);
    }

    private void handleToppingModificationAvailability() {
        if (currentPizza instanceof BuildYourOwn) {
            enableToppingModification(true);
        } else {
            enableToppingModification(false);
        }
    }


    private void enableToppingModification(boolean enable) {
        moveToSelectedButton.setDisable(!enable);
        moveToAvailableButton.setDisable(!enable);
        availableToppingsListView.setDisable(!enable);
        //selectedToppingsListView.setDisable(!enable);
    }



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
                imagePath = "/Chicago-Style-pizza.jpg"; // Fallback image
        }

        // Load and set the image
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        pizzaImageView.setImage(image);
    }

    private void createPizza(String type) {
        // Create a new Pizza object based on the selected type
        switch (type) {
            case "Meatzza":
                currentPizza = new Meatzza(Crust.STUFFED, getCurrentSize()); // Default crust example
                setFixedToppings(currentPizza); // Display toppings for Meatzza
                break;
            case "Deluxe":
                currentPizza = new Deluxe(Crust.DEEP_DISH, getCurrentSize()); // Example for Deluxe
                setFixedToppings(currentPizza); // Display toppings
                break;
            case "Build Your Own":
                currentPizza = new BuildYourOwn(Crust.PAN, getCurrentSize()); // Example for Build Your Own
                resetBuildYourOwnToppings(); // Reset toppings for Build Your Own
                break;
            case "BBQChicken":
                currentPizza = new BBQChicken(Crust.PAN, getCurrentSize()); // Example for BBQChicken
                setFixedToppings(currentPizza); // Display toppings
                break;
            default:
                currentPizza = new BuildYourOwn(Crust.PAN, getCurrentSize()); // Default pizza
                resetBuildYourOwnToppings(); // Reset toppings for Build Your Own
        }

        updatePrice(); // Update the displayed price
    }

    private void updatePrice() {
        if (currentPizza != null) {
            double price = currentPizza.price(); // Use the price() method of the Pizza class
            priceTextField.setText(String.format("$%.2f", price));
        }
    }

    private Size getCurrentSize() {
        // Determine the selected size from the ToggleGroup (assuming RadioButton text matches Size enum names)
        RadioButton selectedSize = (RadioButton) sizeGroup.getSelectedToggle();
        if (selectedSize != null) {
            return Size.valueOf(selectedSize.getText().toUpperCase()); // Assumes Size enum values are UPPER_CASE
        }
        return Size.SMALL; // Default size if none is selected
    }

    private void updateCrust(String pizzaType) {
        Crust crust;

        switch (pizzaType) {
            case "Meatzza":
                crust = Crust.STUFFED; // Example crust for Meatzza
                break;
            case "Deluxe":
                crust = Crust.PAN; // Example crust for Deluxe
                break;
            case "BBQChicken":
                crust = Crust.HAND_TOSSED; // Example crust for BBQChicken
                break;
            case "Build Your Own":
                crust = Crust.PAN; // Default crust for Build Your Own
                break;
            default:
                crust = Crust.PAN; // Fallback crust type
        }

        // Update the UI element (Label, TextField, etc.) to show the crust type
        crustTextField.setText(crust.name());
    }

    private void updatePizzaSize() {
        if (currentPizza != null) {
            currentPizza.setSize(getCurrentSize());
            updatePrice();
        }
    }

    private void resetBuildYourOwnToppings() {
        // Clear selected toppings
        selectedToppingsListView.getItems().clear();

        // Reset available toppings list using a copy of the original toppings
        availableToppingsListView.getItems().clear();
        availableToppingsListView.getItems().addAll(FXCollections.observableArrayList(Topping.values()).stream()
                .map(Topping::name)
                .toList());

        // Clear any toppings in the current BuildYourOwn pizza object (if applicable)
        if (currentPizza instanceof BuildYourOwn) {
            currentPizza.getToppings().clear();
        }
    }



    @FXML
    private void moveToSelected() {
        // Logic for moving items from availableToppingsListView to selectedToppingsListView


        if (currentPizza.getToppings().size() >= MAX_TOPPINGS) {
            // Show an alert or message indicating that the maximum number of toppings has been reached
            showMaxToppingsAlert();
            return; // Exit the method without adding the topping
        }


        String selectedTopping = availableToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            availableToppingsListView.getItems().remove(selectedTopping);
            selectedToppingsListView.getItems().add(selectedTopping);
            currentPizza.addTopping(Topping.valueOf(selectedTopping)); // Add the selected topping to the pizza
            updatePrice(); // Update the price after adding a topping

        }

    }

    private void showMaxToppingsAlert() {
        // Create an alert to inform the user that the maximum number of toppings has been reached
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Maximum Toppings Reached");
        alert.setHeaderText(null);
        alert.setContentText("You can only add up to " + MAX_TOPPINGS + " toppings.");
        alert.showAndWait();
    }

    @FXML
    private void moveToAvailable() {
        // Logic for moving items from selectedToppingsListView back to availableToppingsListView
        String selectedTopping = selectedToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            selectedToppingsListView.getItems().remove(selectedTopping);
            availableToppingsListView.getItems().add(selectedTopping);
            currentPizza.removeTopping(Topping.valueOf(selectedTopping)); // Remove the selected topping from the pizza
            updatePrice(); // Update the price after adding a topping

        }
    }

    @FXML
    private void addToOrder() {
        // Logic for adding the order, e.g., collecting data and updating order list
        System.out.println("Order added with selected options");
    }
}

