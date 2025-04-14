package hexa.org.dao;

import hexa.org.entity.Customer;
import hexa.org.exception.InvalidDataException;

import java.sql.SQLException;

public interface CustomerDAO {
	void registerCustomer(Customer customer) throws InvalidDataException;
    void updateCustomerDetails(int customerId, String newEmail, String newPhone) throws InvalidDataException;
}
