package hexa.org.dao;

import hexa.org.entity.Payment;
import hexa.org.exception.InvalidDataException;
import hexa.org.exception.PaymentFailedException;
import hexa.org.util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public void recordPayment(Payment payment) throws InvalidDataException, PaymentFailedException {
        Connection con = DBConnUtil.getConnection();

        if (con != null) {
            String insertQuery = "INSERT INTO payments (OrderID, Amount, PaymentStatus) VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                ps.setInt(1, payment.getOrderId());
                ps.setDouble(2, payment.getAmount());
                ps.setString(3, payment.getPaymentStatus());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Payment recorded successfully.");
                } else {
                    throw new PaymentFailedException("Payment transaction failed, unable to record.");
                }
            } catch (SQLException e) {
                System.out.println("Error executing payment record insertion: " + e.getMessage());
                e.printStackTrace();
                throw new PaymentFailedException("Payment process failed due to SQL error.");
            } finally {
                try {
                    con.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Database connection failed.");
            throw new PaymentFailedException("Payment process failed due to connection issues.");
        }
    }
    
    public void updatePaymentStatus(int paymentId, String newStatus) throws SQLException {
        Connection con = DBConnUtil.getConnection();
        if (con != null) {
            String updateQuery = "UPDATE payments SET PaymentStatus = ? WHERE PaymentID = ?";
            try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
                ps.setString(1, newStatus);
                ps.setInt(2, paymentId);
                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Payment status updated successfully.");
                } else {
                    System.out.println("Payment not found or update failed.");
                }
            }
        } else {
            System.out.println("Database connection failed.");
        }
    }
}

