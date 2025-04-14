package hexa.org.dao;

import java.util.List;

import hexa.org.entity.Inventory;
import hexa.org.entity.Order;
import hexa.org.entity.OrderDetail;
import hexa.org.entity.Payment;
import hexa.org.entity.Product;
import hexa.org.entity.SalesReport;
import hexa.org.exception.IncompleteOrderException;
import hexa.org.exception.InsufficientStockException;
import hexa.org.exception.PaymentFailedException;

public interface ServiceProvider {

    void addProduct(Product product);
    void removeProduct(int productId);
    Product searchProductByName(String name);
    List<Product> getAllProducts();

    void addOrder(Order order) throws IncompleteOrderException;
    void cancelOrder(int orderId);
    void updateOrderStatus(int orderId, String newStatus);
    void sortOrdersByDateAscending();

    void addInventory(Inventory inventory);
    Inventory getInventory(int productId);
    void removeInventory(int productId);
    void updateInventoryAfterOrder(int productId, int orderedQuantity) throws InsufficientStockException;

    void findProductByName(String name);

    void recordPayment(Payment payment) throws PaymentFailedException;
    void updatePaymentStatus(int paymentId, String newStatus);
    void listAllPayments();

    void addOrderDetail(OrderDetail orderDetail) throws IncompleteOrderException, InsufficientStockException;
    
    String getOrderStatus(int orderId);
    
    List<SalesReport> getSalesByProduct();

}
