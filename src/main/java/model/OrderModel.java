package model;

public class OrderModel {

    // Singleton instance
    private static OrderModel instance;

    // Current order
    private Order currentOrder;

    // Order history
    private final OrderHistory orderHistory;

    // Private constructor to enforce Singleton pattern
    private OrderModel() {
        this.currentOrder = new Order();
        this.orderHistory = new OrderHistory();
    }

    // Method to get the single instance of OrderModel
    public static OrderModel getInstance() {
        if (instance == null) {
            instance = new OrderModel();
        }
        return instance;
    }

    // Method to get the current order
    public Order getCurrentOrder() {
        return currentOrder;
    }

    // Method to add the current order to the order history and create a new order
    public void finalizeCurrentOrder() {
        if (currentOrder != null && !currentOrder.getPizzas().isEmpty()) {
            orderHistory.addOrder(currentOrder);
            currentOrder = new Order(); // Start a new order
        }
    }

    // Method to get the order history
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }
}
