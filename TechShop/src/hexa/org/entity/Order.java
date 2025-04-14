package hexa.org.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String orderStatus;
    private List<OrderDetail> orderDetails;

    public Order() {
		super();
	}

	public Order(int orderId, Customer customer, LocalDateTime orderDate, double totalAmount, String orderStatus) {
		super();
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
	}

	public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
    	if(totalAmount>=0) {
    		this.totalAmount = totalAmount;
    	}
    	else {
    		System.out.println("Amount cannot be negative");
    	}
    }
    
    public String getOrderStatus() {
    	return orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
    	this.orderStatus = orderStatus;
    }
    public double calculateTotalAmount() {
    	return totalAmount;
    }
    
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    
    public void updateOrderStatus(String newStatus) {
        this.orderStatus = newStatus;
    }
    
    public void cancelOrder() {
        System.out.println("Order #" + orderId + " has been cancelled.");
        }
    
    @Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customer=" + customer + ", orderDate=" + orderDate + ", totalAmount="
				+ totalAmount + ", orderStatus=" + orderStatus + "]";
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
