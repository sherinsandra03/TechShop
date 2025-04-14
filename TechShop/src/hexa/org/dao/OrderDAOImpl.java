package hexa.org.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import hexa.org.entity.Order;
import hexa.org.entity.OrderDetail;
import hexa.org.exception.InsufficientStockException;
import hexa.org.util.DBConnUtil;

public class OrderDAOImpl {

    public void addOrder(Order order) throws InsufficientStockException {
        try (Connection con = DBConnUtil.getConnection()) {
            String insertOrderQuery = "INSERT INTO orders (CustomerID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertOrderQuery);
            pstmt.setInt(1, order.getCustomer().getCustomerId());
            pstmt.setString(2, order.getOrderDate().toString());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getOrderStatus());
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Order placed successfully!");
                updateInventory(order);
            } else {
                System.out.println("Order placement failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInventory(Order order) throws InsufficientStockException {
        try (Connection con = DBConnUtil.getConnection()) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                int productId = orderDetail.getProduct().getProductId();
                int orderedQuantity = orderDetail.getQuantity();

                String query = "SELECT QuantityInStock FROM inventory WHERE ProductID = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, productId);
                var rs = pstmt.executeQuery();

                if (rs.next()) {
                    int currentStock = rs.getInt("QuantityInStock");
                    if (currentStock >= orderedQuantity) {
                        String updateQuery = "UPDATE inventory SET QuantityInStock = ? WHERE ProductID = ?";
                        PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                        updateStmt.setInt(1, currentStock - orderedQuantity);
                        updateStmt.setInt(2, productId);
                        updateStmt.executeUpdate();
                    } else {
                        throw new InsufficientStockException("Not enough stock for product: " + productId);
                    }
                } else {
                    throw new InsufficientStockException("Product not found in inventory: " + productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(int orderId) {
        try (Connection con = DBConnUtil.getConnection()) {
            String deleteOrderQuery = "DELETE FROM orders WHERE OrderID = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteOrderQuery);
            pstmt.setInt(1, orderId);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Order canceled successfully.");
                restoreInventory(orderId, con);
            } else {
                System.out.println("Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void restoreInventory(int orderId, Connection con) {
        try {
            String getOrderDetailsQuery = "SELECT od.ProductID, od.Quantity, p.Price FROM orderdetails od JOIN products p ON od.ProductID = p.ProductID WHERE od.OrderID = ?";
            PreparedStatement pstmt = con.prepareStatement(getOrderDetailsQuery);
            pstmt.setInt(1, orderId);
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");

                String updateInventoryQuery = "UPDATE inventory SET QuantityInStock = QuantityInStock + ? WHERE ProductID = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateInventoryQuery);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, productId);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
