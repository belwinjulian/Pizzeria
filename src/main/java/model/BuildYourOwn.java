package model;

import model.enums.Crust;
import model.enums.Size;

/**
 * Represents a Build-Your-Own pizza where customers can select their own toppings.
 * Inherits from the Pizza class and calculates pricing based on size and number of toppings.
 * Author: Belwin Julian, Suhas Murthy
 */
public class BuildYourOwn extends Pizza {

    /**
     * Constructs a Build-Your-Own pizza with the specified crust and size.
     * @param crust the crust type of the pizza
     * @param size the size of the pizza
     */
    public BuildYourOwn(Crust crust, Size size) {
        super(crust, size);
    }

    /**
     * Calculates the price of the Build-Your-Own pizza based on its size and number of toppings.
     * Base price varies by size, with an additional cost per topping.
     * @return the total price of the pizza
     */
    @Override
    public double price() {
        double basePrice;
        switch (getSize()) {
            case SMALL:
                basePrice = 8.99;
                break;
            case MEDIUM:
                basePrice = 10.99;
                break;
            case LARGE:
                basePrice = 12.99;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getSize());
        }
        // Calculate total price with added cost for each topping
        return basePrice + (getToppings().size() * 1.69);
    }
}
