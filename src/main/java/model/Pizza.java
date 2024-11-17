package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.ArrayList;

/**
 * Abstract base class representing a Pizza.
 * Provides common properties and methods for different types of pizzas.
 * Subclasses must implement the abstract price() method to calculate the cost of the pizza.
 * Author: Belwin Julian, Suhas Murthy
 */
public abstract class Pizza {

    private ArrayList<Topping> toppings; // List of toppings for the pizza
    private Crust crust;  // The type of crust for the pizza
    private Size size;    // The size of the pizza

    /**
     * Constructs a Pizza with the specified crust and size.
     * Initializes an empty list of toppings.
     * @param crust the type of crust
     * @param size the size of the pizza
     */
    public Pizza(Crust crust, Size size) {
        this.crust = crust;
        this.size = size;
        this.toppings = new ArrayList<>(); // Initializes an empty list of toppings
    }

    /**
     * Abstract method to calculate the price of the pizza.
     * Must be implemented by subclasses.
     * @return the price of the pizza
     */
    public abstract double price();

    /**
     * Retrieves the list of toppings for the pizza.
     * @return a list of Topping objects
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Adds a topping to the pizza.
     * Limits the number of toppings to a maximum of 7 for Build Your Own pizzas.
     * @param topping the topping to add
     * @throws IllegalStateException if the maximum number of toppings is reached
     */
    public void addTopping(Topping topping) {
        if (toppings.size() < 7) { // Maximum of 7 toppings
            toppings.add(topping);
        } else {
            throw new IllegalStateException("Maximum number of toppings reached.");
        }
    }

    /**
     * Removes a topping from the pizza.
     * @param topping the topping to remove
     */
    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    /**
     * Retrieves the crust type of the pizza.
     * @return the crust type
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Sets the crust type of the pizza.
     * @param crust the crust type to set
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * Retrieves the size of the pizza.
     * @return the size of the pizza
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the pizza.
     * @param size the size to set
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Provides a string representation of the pizza, including crust, size, and toppings.
     * @return a string representation of the pizza
     */
    @Override
    public String toString() {
        return "Pizza [crust=" + crust + ", size=" + size + ", toppings=" + toppings + "]";
    }
}
