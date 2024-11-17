package model;

/**
 * Singleton class that manages the current order and the history of all orders.
 * Provides methods for accessing and managing orders.
 * Author: Belwin Julian, Suhas Murthy
 */
public class OrderModel {

    // Singleton instance of OrderModel
    private static OrderModel instance;

    // Current active order
    private Order currentOrder;

    // History of all orders
    private final OrderHistory orderHistory;

    /**
     * Private constructor to enforce the Singleton pattern.
     * Initializes the current order and order history.
     */
    private OrderModel() {
        this.currentOrder = new Order();
        this.orderHistory = new OrderHistory();
    }

    /**
     * Retrieves the single instance of OrderModel.
     * @return the single instance of OrderModel
     */
    public static OrderModel getInstance() {
        if (instance == null) {
            instance = new OrderModel();
        }
        return instance;
    }

    /**
     * Retrieves the current order.
     * @return the current Order instance
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Finalizes the current order by adding it to the order history and creating a new order.
     * @return true if the current order was successfully finalized and added to the history, false otherwise
     */
    public boolean finalizeCurrentOrder() {
        if (currentOrder != null && !currentOrder.getPizzas().isEmpty()) {
            orderHistory.addOrder(currentOrder);
            currentOrder = new Order(); // Start a new order
            return true;
        }
        return false;
    }

    /**
     * Retrieves the order history.
     * @return the OrderHistory instance containing all past orders
     */
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }
}
