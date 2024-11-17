package model;

import java.util.ArrayList;

/**
 * Represents a customer's order, containing a list of pizzas and providing functionality to add, remove, and calculate costs.
 * Each order is assigned a unique order number.
 * Author: Belwin Julian, Suhas Murthy
 */
public class Order {
    private static int orderCounter = 1; // Static counter to generate unique order numbers
    private int number; // Unique order number for this order
    private ArrayList<Pizza> pizzas; // List of pizzas in this order

    /**
     * Constructs a new Order with a unique order number.
     */
    public Order() {
        this.number = orderCounter++;
        this.pizzas = new ArrayList<>();
    }

    /**
     * Gets the unique order number.
     * @return the order number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Adds a pizza to the order.
     * @param pizza the pizza to add
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza from the order.
     * @param pizza the pizza to remove
     * @return true if the pizza was removed successfully, false otherwise
     */
    public boolean removePizza(Pizza pizza) {
        return pizzas.remove(pizza);
    }

    /**
     * Calculates the total cost of the order, including a 6.625% sales tax.
     * @return the total cost of the order
     */
    public double getTotalCost() {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        double salesTax = subtotal * 0.06625; // 6.625% NJ sales tax
        return subtotal + salesTax;
    }

    /**
     * Calculates the sales tax for the order based on a 6.625% tax rate.
     * @return the sales tax amount
     */
    public double getSalesTax() {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        return subtotal * 0.06625;
    }

    /**
     * Gets the list of pizzas in the order.
     * @return the list of pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Clears all pizzas from the order.
     */
    public void clearOrder() {
        pizzas.clear();
    }

    /**
     * Provides a string representation of the order, including the order number, pizzas, and total cost.
     * @return a string representation of the order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Number: ").append(number).append("\n");
        for (Pizza pizza : pizzas) {
            sb.append(pizza.toString()).append("\n");
        }
        sb.append("Total Cost (including tax): $").append(String.format("%.2f", getTotalCost()));
        return sb.toString();
    }
}
