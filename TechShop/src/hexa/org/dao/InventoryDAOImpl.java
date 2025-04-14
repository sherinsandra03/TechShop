package hexa.org.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hexa.org.entity.Inventory;
import hexa.org.util.DBConnUtil;

public class InventoryDAOImpl implements InventoryDAO {

    private Connection connection;

    public void addInventory(Inventory inventory) {
        Connection con = DBConnUtil.getConnection();
        if (con != null) {
            try {
                String query = "INSERT INTO inventory (ProductID, QuantityInStock, LastStockUpdate) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, inventory.getProduct().getProductId());
                stmt.setInt(2, inventory.getQuantityInStock());
                stmt.setTimestamp(3, java.sql.Timestamp.valueOf(inventory.getLastStockUpdate()));
                stmt.executeUpdate();
                System.out.println("Inventory added successfully!");
            } catch (SQLException e) {
                System.out.println("Error adding inventory: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void removeInventory(int productId) {
        Connection con = DBConnUtil.getConnection();
        if (con != null) {
            try {
                String query = "DELETE FROM inventory WHERE ProductID = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, productId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error removing inventory: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void updateInventory(int productId, int quantity) {
        Connection con = DBConnUtil.getConnection();
        if (con != null) {
            try {
                String query = "UPDATE inventory SET QuantityInStock = ? WHERE ProductID = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, quantity);
                stmt.setInt(2, productId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error updating inventory: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
