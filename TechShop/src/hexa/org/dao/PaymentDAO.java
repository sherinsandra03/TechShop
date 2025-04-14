package hexa.org.dao;

import java.sql.SQLException;

import hexa.org.entity.Payment;
import hexa.org.exception.InvalidDataException;
import hexa.org.exception.PaymentFailedException;

public interface PaymentDAO {
	void recordPayment(Payment payment) throws InvalidDataException, PaymentFailedException;
	void updatePaymentStatus(int paymentId, String newStatus) throws SQLException;
}
