package hexa.org.entity;

public class Payment {
    private int paymentId;
    private int orderId;
    private double amount;
    private String paymentStatus;

    public Payment() {
    }

    public Payment(int paymentId, int orderId, double amount, String paymentStatus) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment [ID=" + paymentId + ", OrderID=" + orderId + ", Amount=" + amount + ", Status=" + paymentStatus + "]";
    }
}
