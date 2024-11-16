package model;

import java.util.ArrayList;

public class OrderHistory {
    private ArrayList<Order> orders;

    // Constructor
    public OrderHistory() {
        this.orders = new ArrayList<>();
    }

    // Add an order to the history
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Remove an order from the history
    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    // Get all orders
    public ArrayList<Order> getOrders() {
        return orders;
    }

    // Find an order by its number
    public Order findOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getNumber() == orderNumber) {
                return order;
            }
        }
        return null; // Return null if not found
    }

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

