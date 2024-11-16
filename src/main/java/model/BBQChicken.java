package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.Arrays;

public class BBQChicken extends Pizza {

    public BBQChicken(Crust crust, Size size) {
        super(crust, size);
        // Assuming BBQChicken pizza has fixed toppings
        getToppings().addAll(Arrays.asList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.ONION, Topping.PROVOLONE, Topping.CHEDDAR));
    }

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

