package model;

import model.enums.Crust;
import model.enums.Size;
import model.interfaces.PizzaFactory;

/**
 * Factory class for creating different types of Chicago-style pizzas.
 * Implements the PizzaFactory interface to provide methods for creating specific pizza types.
 * Author: Belwin Julian, Suhas Murthy
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with a DEEP_DISH crust.
     * @param size the size of the pizza
     * @return a Deluxe pizza instance with specified size and crust
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.DEEP_DISH, size);
    }

    /**
     * Creates a Meatzza pizza with a STUFFED crust.
     * @param size the size of the pizza
     * @return a Meatzza pizza instance with specified size and crust
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.STUFFED, size);
    }

    /**
     * Creates a BBQ Chicken pizza with a PAN crust.
     * @param size the size of the pizza
     * @return a BBQ Chicken pizza instance with specified size and crust
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.PAN, size);
    }

    /**
     * Creates a Build-Your-Own pizza with a PAN crust.
     * @param size the size of the pizza
     * @return a Build-Your-Own pizza instance with specified size and crust
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.PAN, size);
    }
}
