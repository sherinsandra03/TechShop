package hexa.org.dao;

import hexa.org.entity.Product;
import hexa.org.exception.InvalidDataException;

import java.util.List;

public interface ProductDAO {
	void updateProduct(Product product) throws InvalidDataException;
	void addProductToDatabase(Product product);
	void removeProductFromDatabase(int productId);
    
    List<Product> searchProducts(String searchTerm);
    List<Product> getRecommendations(String category);
    Product getProductById(int productId);
}
