package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.Arrays;

/**
 * Represents a Deluxe pizza with predefined toppings.
 * Inherits from the Pizza class and overrides pricing based on size.
 * Author: Belwin Julian, Suhas Murthy
 */
public class Deluxe extends Pizza {

    /**
     * Constructs a Deluxe pizza with the specified crust and size.
     * Predefined toppings are added by default.
     * @param crust the crust type of the pizza
     * @param size the size of the pizza
     */
    public Deluxe(Crust crust, Size size) {
        super(crust, size);
        // Adding predefined toppings for Deluxe pizza
        getToppings().addAll(Arrays.asList(
                Topping.SAUSAGE,
                Topping.PEPPERONI,
                Topping.GREEN_PEPPER,
                Topping.ONION,
                Topping.MUSHROOM
        ));
    }

    /**
     * Calculates the price of the Deluxe pizza based on its size.
     * @return the price of the pizza
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 16.99;
            case MEDIUM:
                return 18.99;
            case LARGE:
                return 20.99;
            default:
                throw new IllegalStateException("Unexpected value: " + getSize());
        }
    }
}
