package hexa.org.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hexa.org.entity.Customer;
import hexa.org.exception.InvalidDataException;
import hexa.org.util.DBConnUtil;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public void registerCustomer(Customer customer) throws InvalidDataException {
	    String insertQuery = "INSERT INTO customers (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";

	    try (Connection con = DBConnUtil.getConnection()) {
	        PreparedStatement pstmt = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, customer.getFirstName());
	        pstmt.setString(2, customer.getLastName());
	        pstmt.setString(3, customer.getEmail());
	        pstmt.setString(4, customer.getPhone());
	        pstmt.setString(5, customer.getAddress());

	        int rowsInserted = pstmt.executeUpdate();
	        if (rowsInserted > 0) {
	            ResultSet generatedKeys = pstmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int generatedCustomerId = generatedKeys.getInt(1);
	                customer.setCustomerId(generatedCustomerId); 
	                System.out.println("Customer registered successfully with ID: " + generatedCustomerId);
	            }
	        } else {
	            throw new InvalidDataException("Customer registration failed. No rows were inserted.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new InvalidDataException("An error occurred while registering the customer: " + e.getMessage());
	    }
	}



    
    @Override
    public void updateCustomerDetails(int customerId, String newEmail, String newPhone) throws InvalidDataException {
        Connection con = DBConnUtil.getConnection();
        if (con != null) {
            try {
                String checkEmailQuery = "SELECT COUNT(*) FROM customers WHERE Email = ?";
                PreparedStatement checkEmailStmt = con.prepareStatement(checkEmailQuery);
                checkEmailStmt.setString(1, newEmail);
                var resultSet = checkEmailStmt.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    throw new InvalidDataException("Email is already taken by another customer.");
                }

                String updateQuery = "UPDATE customers SET Email = ?, Phone = ? WHERE CustomerID = ?";
                PreparedStatement pstmt = con.prepareStatement(updateQuery);
                pstmt.setString(1, newEmail);
                pstmt.setString(2, newPhone);
                pstmt.setInt(3, customerId);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Customer account updated successfully.");
                } else {
                    System.out.println("Customer not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                throw e;
            }
        }
    }
}
