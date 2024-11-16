package model;

import java.util.ArrayList;

public class Order {
    private static int orderCounter = 1; // Static counter to generate unique order numbers
    private int number; // Unique order number
    private ArrayList<Pizza> pizzas; // List of pizzas in this order

    // Constructor
    public Order() {
        this.number = orderCounter++;
        this.pizzas = new ArrayList<>();
    }

    // Getter for order number
    public int getNumber() {
        return number;
    }

    // Add a pizza to the order
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    // Remove a pizza from the order
    public boolean removePizza(Pizza pizza) {
        return pizzas.remove(pizza);
    }

    // Calculate the total cost of the order (including sales tax)
    public double getTotalCost() {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        double salesTax = subtotal * 0.06625; // 6.625% NJ sales tax
        return subtotal + salesTax;
    }

    //Calculate Sales Tax
    public double getSalesTax(){
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        // 6.625% NJ sales tax
        return subtotal * 0.06625;
    }

    // Get the list of pizzas in the order
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

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
