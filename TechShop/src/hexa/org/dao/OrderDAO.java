package hexa.org.dao;

import hexa.org.entity.Order;
import hexa.org.exception.InsufficientStockException;

public interface OrderDAO {

    void addOrder(Order order) throws InsufficientStockException;
    void cancelOrder(int orderId);
    double calculateOrderTotal(Order order);
}
