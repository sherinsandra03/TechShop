package hexa.org.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import hexa.org.entity.Inventory;
import hexa.org.entity.Order;
import hexa.org.entity.OrderDetail;
import hexa.org.entity.Payment;
import hexa.org.entity.Product;
import hexa.org.entity.SalesReport;
import hexa.org.exception.*;
import hexa.org.util.DBConnUtil;

public class ServiceProviderImpl implements ServiceProvider {

    private List<Product> productList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private TreeMap<Integer, Inventory> inventoryMap = new TreeMap<>();
    private List<Payment> paymentList = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        for (Product p : productList) {
            if (p.getProductId() == product.getProductId() || p.getProductName().equalsIgnoreCase(product.getProductName())) {
                System.out.println("Product already exists");
                return;
            }
        }
        productList.add(product);
        System.out.println("Product added successfully");
    }

    @Override
    public void removeProduct(int productId) {
        for (Product p : productList) {
            if (p.getProductId() == productId) {
                productList.remove(p);
                System.out.println("Product removed");
                return;
            }
        }
        System.out.println("Product not found");
    }

    @Override
    public Product searchProductByName(String name) {
        for (Product p : productList) {
            if (p.getProductName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public void addOrder(Order order) throws IncompleteOrderException {
        if (order.getCustomer() == null || order.getOrderDate() == null || order.getTotalAmount() < 0) {
            throw new IncompleteOrderException("Incomplete order details.");
        }
        orderList.add(order);
        System.out.println("Order placed successfully");
    }

    @Override
    public void cancelOrder(int orderId) {
        for (Order o : orderList) {
            if (o.getOrderId() == orderId) {
                orderList.remove(o);
                System.out.println("Order Cancelled");
                return;
            }
        }
        System.out.println("Order not found");
    }

    @Override
    public void updateOrderStatus(int orderId, String newStatus) {
        for (Order o : orderList) {
            if (o.getOrderId() == orderId) {
                o.setOrderStatus(newStatus);
                System.out.println("Order status: " + newStatus);
                return;
            }
        }
        System.out.println("Order not found");
    }

    @Override
    public void sortOrdersByDateAscending() {
        orderList.sort((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));
        System.out.println("Orders sorted by ascending order date:");
        for (Order o : orderList) {
            System.out.println(o);
        }
    }

    @Override
    public void addInventory(Inventory inventory) {
        Product product = inventory.getProduct();
        int productId = product.getProductId();
        inventoryMap.put(productId, inventory);
        System.out.println("Inventory added for " + productId);
    }

    @Override
    public Inventory getInventory(int productId) {
        return inventoryMap.get(productId);
    }

    @Override
    public void removeInventory(int productId) {
        if (inventoryMap.containsKey(productId)) {
            inventoryMap.remove(productId);
            System.out.println("Inventory removed for Product ID: " + productId);
        } else {
            System.out.println("No inventory found for Product ID");
        }
    }

    @Override
    public void updateInventoryAfterOrder(int productId, int orderedQuantity) throws InsufficientStockException {
        Inventory inventory = inventoryMap.get(productId);
        if (inventory != null) {
            int availableStock = inventory.getQuantityInStock();
            if (availableStock >= orderedQuantity) {
                inventory.setQuantityInStock(availableStock - orderedQuantity);
                System.out.println("Inventory updated for product ID: " + productId);
            } else {
                throw new InsufficientStockException("Only " + availableStock + " items available.");
            }
        } else {
            System.out.println("No inventory found for product ID: " + productId);
        }
    }

    @Override
    public void findProductByName(String name) {
        boolean flag = false;
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(product);
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("No product found");
        }
    }

    @Override
    public void recordPayment(Payment payment) throws PaymentFailedException {
        boolean flag = false;
        for (Order o : orderList) {
            if (o.getOrderId() == payment.getOrderId()) {
                flag = true;
                o.setOrderStatus("Paid");
                break;
            }
        }
        if (flag) {
            paymentList.add(payment);
            System.out.println("Payment recorded");
        } else {
            throw new PaymentFailedException("Cannot record payment. Order does not exist");
        }
    }

    @Override
    public void updatePaymentStatus(int paymentId, String newStatus) {
        for (Payment p : paymentList) {
            if (p.getPaymentId() == paymentId) {
                p.setPaymentStatus(newStatus);
                System.out.println("Payment status updated");
                return;
            }
        }
        System.out.println("Payment ID not found.");
    }

    @Override
    public void listAllPayments() {
        if (paymentList.isEmpty()) {
            System.out.println("No payments recorded.");
        } else {
            for (Payment p : paymentList) {
                System.out.println(p);
            }
        }
    }

    @Override
    public void addOrderDetail(OrderDetail orderDetail) throws IncompleteOrderException, InsufficientStockException {
        if (orderDetail.getProduct() == null) {
            throw new IncompleteOrderException("Order detail missing product reference.");
        }

        int productId = orderDetail.getProduct().getProductId();
        int orderedQty = orderDetail.getQuantity();
        Inventory inventory = inventoryMap.get(productId);

        if (inventory != null) {
            if (inventory.getQuantityInStock() >= orderedQty) {
                updateInventoryAfterOrder(productId, orderedQty);
                System.out.println("Order detail added");
            } else {
                throw new InsufficientStockException("Cannot add order detail. Insufficient stock.");
            }
        } else {
            throw new IncompleteOrderException("Cannot add order detail. Product not in inventory.");
        }
    }
    
    
    @Override
    public String getOrderStatus(int orderId) {
        String orderStatus = null;
        
        String sql = "SELECT status FROM orders WHERE orderId = ?";
        
        try (Connection con = DBConnUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                orderStatus = rs.getString("status");
            } else {
                System.out.println("Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderStatus;
    }
    
    @Override
    public List<SalesReport> getSalesByProduct() {
        List<SalesReport> salesReports = new ArrayList<>();

        String query = "SELECT p.ProductName, SUM(od.Quantity) AS totalQuantity, SUM(od.Quantity * p.Price) AS totalSales " +
                "FROM orderdetails od " +
                "JOIN products p ON od.ProductID = p.ProductID " +
                "GROUP BY p.ProductID";

        try (Connection con = DBConnUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String productName = rs.getString("ProductName");
                int totalQuantity = rs.getInt("totalQuantity");
                double totalSales = rs.getDouble("totalSales");

                SalesReport report = new SalesReport(productName, totalQuantity, totalSales);
                salesReports.add(report);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReports;
    }


}


