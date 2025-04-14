package hexa.org.dao;

import hexa.org.entity.Product;
import hexa.org.exception.InvalidDataException;
import hexa.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
	
    public void addProductToDatabase(Product product) {
        String query = "INSERT INTO products (ProductName, Description, Price, Category) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getCategory());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Product added to the database successfully.");
            } else {
                System.out.println("Failed to add product to the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while adding product: " + e.getMessage());
        }
    }

    public void removeProductFromDatabase(int productId) {
        String query = "DELETE FROM products WHERE ProductID = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, productId);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product removed from the database successfully.");
            } else {
                System.out.println("Product not found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while removing product: " + e.getMessage());
        }
    }

	 @Override
	    public void updateProduct(Product product) throws InvalidDataException {
	        Connection con = DBConnUtil.getConnection();
	        if (con != null) {
	            try {
	                String updateQuery = "UPDATE products SET ProductName = ?, Description = ?, Price = ?, Category = ? WHERE ProductID = ?";
	                PreparedStatement pstmt = con.prepareStatement(updateQuery);
	                pstmt.setString(1, product.getProductName());
	                pstmt.setString(2, product.getDescription());
	                pstmt.setDouble(3, product.getPrice());
	                pstmt.setString(4, product.getCategory());
	                pstmt.setInt(5, product.getProductId());

	                int rowsUpdated = pstmt.executeUpdate();
	                if (rowsUpdated > 0) {
	                    System.out.println("Product updated successfully.");
	                } else {
	                    throw new InvalidDataException("Product update failed, product not found.");
	                }

	            } catch (SQLException e) {
	                System.out.println("Error while updating product.");
	                e.printStackTrace();
	            } catch (InvalidDataException e) {
	                throw e;  
	            }
	        }
	    }

    @Override
    public List<Product> searchProducts(String searchTerm) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE ProductName LIKE ? OR Category LIKE ?";
        
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, "%" + searchTerm + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getString("Category")
                    );
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> getRecommendations(String category) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE Category LIKE ?";
        
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, "%" + category + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getString("Category")
                    );
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    public Product getProductById(int productId) {
        Product product = null;
        String query = "SELECT * FROM products WHERE ProductID = ?";  
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, productId); 
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) {
                product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price"),
                    rs.getString("Category")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
        
        return product; 
    }
    
}
