module pizzeria.pizzeria {
    requires javafx.controls;
    requires javafx.fxml;

    // Open the controller package for reflection access by JavaFX
    opens pizzeria.pizzeria.controller to javafx.graphics, javafx.fxml;
}
