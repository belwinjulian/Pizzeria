import model.BuildYourOwn;
import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildYourOwnTest {

    private BuildYourOwn pizza;

    @BeforeEach
    public void setUp() {
        // Initialize a new BuildYourOwn pizza before each test
        pizza = new BuildYourOwn(Crust.PAN, Size.SMALL); // Using default crust and size
    }

    @Test
    public void testPriceNoToppingsSmallSize() {
        // No toppings, small size
        assertEquals(8.99, pizza.price(), 0.01, "Price should be 8.99 for a small BuildYourOwn with no toppings");
    }

    @Test
    public void testPriceThreeToppingsSmallSize() {
        // Add 3 toppings, small size
        pizza = new BuildYourOwn(Crust.PAN, Size.SMALL); // Using default crust and size
        pizza.addTopping(Topping.PEPPERONI);
        pizza.addTopping(Topping.MUSHROOM);
        pizza.addTopping(Topping.ONION);

        assertEquals(14.06, pizza.price(), 0.01, "Price should be 8.99 + 3 * 0.75 for a small BuildYourOwn with three toppings");
    }

    @Test
    public void testPriceFiveToppingsMediumSize() {
        // Change size and add 5 toppings
        pizza.setSize(Size.MEDIUM);
        pizza.addTopping(Topping.SAUSAGE);
        pizza.addTopping(Topping.GREEN_PEPPER);
        pizza.addTopping(Topping.BLACK_OLIVES);
        pizza.addTopping(Topping.HAM);
        pizza.addTopping(Topping.BACON);

        assertEquals(19.44, pizza.price(), 0.01, "Price should be 10.99 + 5 * 0.75 for a medium BuildYourOwn with five toppings");
    }

    @Test
    public void testPriceMaxToppingsLargeSize() {
        // Large size with max toppings (assume max toppings allowed is 7)
        pizza.setSize(Size.LARGE);
        pizza.addTopping(Topping.PEPPERONI);
        pizza.addTopping(Topping.MUSHROOM);
        pizza.addTopping(Topping.ONION);
        pizza.addTopping(Topping.SAUSAGE);
        pizza.addTopping(Topping.GREEN_PEPPER);
        pizza.addTopping(Topping.BLACK_OLIVES);
        pizza.addTopping(Topping.BACON);

        assertEquals(24.82, pizza.price(), 0.01, "Price should be 12.99 + 7 * 0.75 for a large BuildYourOwn with seven toppings");
    }

    @Test
    public void testPriceInvalidOverflowToppings() {
        // Negative test: try to "remove" toppings by directly manipulating (if possible)
        pizza.setSize(Size.LARGE);
        pizza.addTopping(Topping.PEPPERONI);
        pizza.addTopping(Topping.MUSHROOM);
        pizza.addTopping(Topping.ONION);
        pizza.addTopping(Topping.SAUSAGE);
        pizza.addTopping(Topping.GREEN_PEPPER);
        pizza.addTopping(Topping.BLACK_OLIVES);
        pizza.addTopping(Topping.BACON);


        // Assert that adding the 8th topping throws the expected exception
        assertThrows(IllegalStateException.class, () -> {
            pizza.addTopping(Topping.HAM); // Attempt to add an 8th topping
        }, "Adding an 8th topping should throw an IllegalStateException");    }
}
