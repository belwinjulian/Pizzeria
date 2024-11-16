package model;

import model.enums.Crust;
import model.enums.Size;
import model.enums.Topping;

import java.util.ArrayList;

public abstract class Pizza {

    // Enum declarations, assuming these are defined somewhere else in your project
    private ArrayList<Topping> toppings; // You can use your custom List<E> implementation if needed
    private Crust crust;  // Crust is an Enum representing different types of crust
    private Size size;    // Size is an Enum representing different sizes of the pizza

    // Constructor
    public Pizza(Crust crust, Size size) {
        this.crust = crust;
        this.size = size;
        this.toppings = new ArrayList<>(); // Initializes an empty list of toppings
    }

    // Abstract method to be implemented by subclasses
    public abstract double price();

    // Getters and setters
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void addTopping(Topping topping) {
        if (toppings.size() < 7) { // Assuming a maximum of 7 toppings for Build Your Own pizzas
            toppings.add(topping);
        } else {
            throw new IllegalStateException("Maximum number of toppings reached.");
        }
    }

    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Pizza [crust=" + crust + ", size=" + size + ", toppings=" + toppings + "]";
    }
}

