package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.Arrays;

public class Meatzza extends Pizza {

    public Meatzza(Crust crust, Size size) {
        super(crust, size);
        // Assuming Meatzza pizza has fixed toppings
        getToppings().addAll(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
    }

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

