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

public class NewYorkController {

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

    private ObservableList<String> availableToppings = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Populate available toppings
        for (Topping topping : Topping.values()) {
            availableToppings.add(topping.name()); // Adds the string representation of each topping
        }
        availableToppingsListView.setItems(availableToppings);

        crustTextField.setEditable(false);
        buildYourOwnComboBox.getItems().addAll("Build Your Own", "Meatzza", "Deluxe", "BBQChicken");
        buildYourOwnComboBox.setValue("Build Your Own");

        updateImage("Build Your Own");
        updateCrust("Build Your Own");
        createPizza("Build Your Own");

        buildYourOwnComboBox.setOnAction(e -> {
            String selectedPizzaType = buildYourOwnComboBox.getValue();
            updateCrust(selectedPizzaType);
            updateImage(selectedPizzaType);
            createPizza(selectedPizzaType);
            handleToppingModificationAvailability();
        });

        sizeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updatePizzaSize();
            }
        });
    }

    private void createPizza(String type) {
        switch (type) {
            case "Meatzza":
                currentPizza = new Meatzza(Crust.HAND_TOSSED, getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            case "Deluxe":
                currentPizza = new Deluxe(Crust.BROOKLYN, getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            case "BBQChicken":
                currentPizza = new BBQChicken(Crust.THIN, getCurrentSize());
                setFixedToppings(currentPizza);
                break;
            case "Build Your Own":
                currentPizza = new BuildYourOwn(Crust.HAND_TOSSED, getCurrentSize());
                resetBuildYourOwnToppings();
                break;
            default:
                currentPizza = new BuildYourOwn(Crust.HAND_TOSSED, getCurrentSize());
                resetBuildYourOwnToppings();
        }
        updatePrice();
    }

    private void setFixedToppings(Pizza pizza) {
        selectedToppingsListView.getItems().clear();
        for (Topping topping : pizza.getToppings()) {
            selectedToppingsListView.getItems().add(topping.name());
        }
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
        availableToppingsListView.setDisable(!enable);
        selectedToppingsListView.setDisable(!enable);
    }

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
        pizzaImageView.setImage(image);
    }

    private void updatePizzaSize() {
        if (currentPizza != null) {
            currentPizza.setSize(getCurrentSize());
            updatePrice();
        }
    }

    private Size getCurrentSize() {
        RadioButton selectedSize = (RadioButton) sizeGroup.getSelectedToggle();
        if (selectedSize != null) {
            return Size.valueOf(selectedSize.getText().toUpperCase());
        }
        return Size.SMALL;
    }

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
        crustTextField.setText(crust.name());
    }

    private void updatePrice() {
        if (currentPizza != null) {
            double price = currentPizza.price();
            priceTextField.setText(String.format("$%.2f", price));
        }
    }

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
            updatePrice();
        }
    }

    private void showMaxToppingsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Maximum Toppings Reached");
        alert.setHeaderText(null);
        alert.setContentText("You can only add up to " + MAX_TOPPINGS + " toppings.");
        alert.showAndWait();
    }

    @FXML
    private void moveToAvailable() {
        String selectedTopping = selectedToppingsListView.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            selectedToppingsListView.getItems().remove(selectedTopping);
            availableToppingsListView.getItems().add(selectedTopping);
            currentPizza.removeTopping(Topping.valueOf(selectedTopping));
            updatePrice();
        }
    }

    @FXML
    private void addToOrder() {
        System.out.println("Order added with selected options");
    }
}
