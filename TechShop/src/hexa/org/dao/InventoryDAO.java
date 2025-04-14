package hexa.org.dao;

import hexa.org.entity.Inventory;

public interface InventoryDAO {
	void addInventory(Inventory inventory);       
    void updateInventory(int productId, int quantity); 
    void removeInventory(int productId);

}
