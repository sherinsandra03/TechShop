package hexa.org.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inventory {
    private int inventoryId;
    private Product product;
    private int quantityInStock;
    private LocalDateTime lastStockUpdate;

    public Inventory() {
		super();
	}

	public Inventory(int inventoryId, Product product, int quantityInStock, LocalDateTime lastStockUpdate) {
		super();
		this.inventoryId = inventoryId;
		this.product = product;
		this.quantityInStock = quantityInStock;
		this.lastStockUpdate = lastStockUpdate;
	}


	public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
    	if(quantityInStock>=0) {
    		this.quantityInStock = quantityInStock;
    	}
    	else {
    		System.out.println("Quantity should not be negative");
    	}
    }

    public LocalDateTime getLastStockUpdate() {
        return lastStockUpdate;
    }

    public void setLastStockUpdate(LocalDateTime lastStockUpdate) {
        this.lastStockUpdate = lastStockUpdate;
    }
    
    public void addToInventory(int quantity) {
    	this.quantityInStock += quantity;
    	this.lastStockUpdate = LocalDateTime.now();
    }
    
    public void removeFromInventory(int quantity) {
    	this.quantityInStock -= quantity;
    	this.lastStockUpdate = LocalDateTime.now();
    }
    
    public void updateStockQuantity(int newQuantity) {
    	setQuantityInStock(newQuantity);
    	this.lastStockUpdate = LocalDateTime.now();
    }
    
    public boolean isProductAvailable(int quantityToCheck) {
    	return quantityInStock>=quantityToCheck;
    }
    
    public double getInventoryValue() {
    	return product.getPrice()*getQuantityInStock();
    }
    
    public void listLowStockProducts(int threshold) {
    	if(quantityInStock<threshold) {
    	System.out.println(product.getProductName()+ "is low in stock");
    	}
    }
    public void listOutOfStockProducts() {
    	if(quantityInStock==0) {
    		System.out.println(product.getProductName()+" is out of stock");
    	}
    }
    
    public void listAllProducts() {
    	System.out.println("Product: " + product.getProductName());
        System.out.println("Quantity in stock: " + quantityInStock);
    	
    }
    
    @Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", product=" + product + ", quantityInStock=" + quantityInStock
				+ ", lastStockUpdate=" + lastStockUpdate + "]";
	}
}
