package model;

import java.util.ArrayList;

/**
 * Represents the history of orders placed by a customer or in the system.
 * Provides functionality to add, remove, and find orders within the history.
 * Author: Belwin Julian, Suhas Murthy
 */
public class OrderHistory {
    private ArrayList<Order> orders; // List to store orders in the history

    /**
     * Constructs a new OrderHistory instance with an empty list of orders.
     */
    public OrderHistory() {
        this.orders = new ArrayList<>();
    }

    /**
     * Adds an order to the order history.
     * @param order the order to be added
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes an order from the order history.
     * @param order the order to be removed
     * @return true if the order was successfully removed, false otherwise
     */
    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    /**
     * Retrieves all orders in the order history.
     * @return a list of all orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Finds an order by its unique order number.
     * @param orderNumber the order number to search for
     * @return the matching Order if found, or null if no match is found
     */
    public Order findOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getNumber() == orderNumber) {
                return order;
            }
        }
        return null; // Return null if no matching order is found
    }

    /**
     * Provides a string representation of the order history, including all orders.
     * @return a string representation of the order history
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order History:\n");
        for (Order order : orders) {
            sb.append(order.toString()).append("\n");
        }
        return sb.toString();
    }
}
