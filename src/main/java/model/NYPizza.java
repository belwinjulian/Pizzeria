package model;

import model.enums.Crust;
import model.enums.Size;
import model.interfaces.PizzaFactory;

/**
 * Factory class for creating different types of New York-style pizzas.
 * Implements the PizzaFactory interface to provide methods for creating specific pizza types with predefined crusts.
 * Author: Belwin Julian, Suhas Murthy
 */
public class NYPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with a BROOKLYN crust.
     * @param size the size of the pizza
     * @return a Deluxe pizza instance with specified size and crust
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.BROOKLYN, size);
    }

    /**
     * Creates a Meatzza pizza with a HAND_TOSSED crust.
     * @param size the size of the pizza
     * @return a Meatzza pizza instance with specified size and crust
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.HAND_TOSSED, size);
    }

    /**
     * Creates a BBQ Chicken pizza with a THIN crust.
     * @param size the size of the pizza
     * @return a BBQ Chicken pizza instance with specified size and crust
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.THIN, size);
    }

    /**
     * Creates a Build-Your-Own pizza with a HAND_TOSSED crust.
     * @param size the size of the pizza
     * @return a Build-Your-Own pizza instance with specified size and crust
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.HAND_TOSSED, size);
    }
}
