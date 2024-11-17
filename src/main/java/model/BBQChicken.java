package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.Arrays;

/**
 * Represents a BBQ Chicken pizza with predefined toppings.
 * Inherits from the Pizza class and overrides pricing based on size.
 * Author: Belwin Julian, Suhas Murthy
 */
public class BBQChicken extends Pizza {

    /**
     * Constructs a BBQ Chicken pizza with the specified crust and size.
     * Predefined toppings are added by default.
     * @param crust the crust type of the pizza
     * @param size the size of the pizza
     */
    public BBQChicken(Crust crust, Size size) {
        super(crust, size);
        // Adding predefined toppings for BBQ Chicken pizza
        getToppings().addAll(Arrays.asList(
                Topping.BBQ_CHICKEN,
                Topping.GREEN_PEPPER,
                Topping.ONION,
                Topping.PROVOLONE,
                Topping.CHEDDAR
        ));
    }

    /**
     * Calculates the price of the BBQ Chicken pizza based on its size.
     * @return the price of the pizza
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 14.99;
            case MEDIUM:
                return 16.99;
            case LARGE:
                return 19.99;
            default:
                throw new IllegalStateException("Unexpected value: " + getSize());
        }
    }
}
