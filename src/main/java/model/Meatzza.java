package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.Arrays;

/**
 * Represents a Meatzza pizza with predefined meat toppings.
 * Inherits from the Pizza class and overrides pricing based on size.
 * Author: Belwin Julian, Suhas Murthy
 */
public class Meatzza extends Pizza {

    /**
     * Constructs a Meatzza pizza with the specified crust and size.
     * Predefined meat toppings are added by default.
     * @param crust the crust type of the pizza
     * @param size the size of the pizza
     */
    public Meatzza(Crust crust, Size size) {
        super(crust, size);
        // Adding predefined meat toppings for Meatzza pizza
        getToppings().addAll(Arrays.asList(
                Topping.SAUSAGE,
                Topping.PEPPERONI,
                Topping.BEEF,
                Topping.HAM
        ));
    }

    /**
     * Calculates the price of the Meatzza pizza based on its size.
     * @return the price of the pizza
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 17.99;
            case MEDIUM:
                return 19.99;
            case LARGE:
                return 21.99;
            default:
                throw new IllegalStateException("Unexpected value: " + getSize());
        }
    }
}
