module pizzeria.pizzeria {
    requires javafx.controls;
    requires javafx.fxml;

    // Open the controller package for reflection access by JavaFX
    opens controller to javafx.graphics, javafx.fxml;

    exports model;
    exports model.enums;
    exports controller;
    opens app to javafx.fxml, javafx.graphics;
}
