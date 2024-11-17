package model.interfaces;

import model.Pizza;
import model.enums.Size;

/**
 * Interface representing a factory for creating different types of pizzas.
 * Implementing classes will provide concrete methods to create specific pizza types.
 * Author: Belwin Julian, Suhas Murthy
 */
public interface PizzaFactory {

    /**
     * Creates a Deluxe pizza with the specified size.
     * @param size the size of the pizza
     * @return a new instance of a Deluxe pizza
     */
    Pizza createDeluxe(Size size);

    /**
     * Creates a Meatzza pizza with the specified size.
     * @param size the size of the pizza
     * @return a new instance of a Meatzza pizza
     */
    Pizza createMeatzza(Size size);

    /**
     * Creates a BBQ Chicken pizza with the specified size.
     * @param size the size of the pizza
     * @return a new instance of a BBQ Chicken pizza
     */
    Pizza createBBQChicken(Size size);

    /**
     * Creates a Build-Your-Own pizza with the specified size.
     * @param size the size of the pizza
     * @return a new instance of a Build-Your-Own pizza
     */
    Pizza createBuildYourOwn(Size size);
}
