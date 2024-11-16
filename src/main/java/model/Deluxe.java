package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.Arrays;

public class Deluxe extends Pizza {

    public Deluxe(Crust crust, Size size) {
        super(crust, size);
        // Assuming Deluxe pizza has fixed toppings
        getToppings().addAll(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
    }

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

