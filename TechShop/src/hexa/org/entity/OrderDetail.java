package hexa.org.entity;

public class OrderDetail {
    private int orderDetailId;
    private Order order;
    private Product product;
    private int quantity;

    public OrderDetail() {
		super();
	}

	public OrderDetail(int orderDetailId, Order order, Product product, int quantity) {
		super();
		this.orderDetailId = orderDetailId;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}
	
	 public OrderDetail(Product product, int quantity) {
	        this.product = product;
	        this.quantity = quantity;
	    }


	public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
    	if(quantity>0) {
    		this.quantity = quantity;
    	}
    	else {
    		System.out.println("Quantity should not be negative");
    	}
    }
    
    public double calculateSubtotal() {
        return product.getPrice() * quantity;
    }
    
    public void getOrderDetailInfo() {
        System.out.println("Order Detail ID: " +orderDetailId);
        System.out.println("Product Name: " +product.getProductName());
        System.out.println("Quantity: " +quantity);
        System.out.println("Subtotal: " +calculateSubtotal());
    }
    
    public void updateQuantity(int newQty) {
        setQuantity(newQty);
    }
    
    public double addDiscount(double percent) {
        double subtotal = calculateSubtotal();
        double discountAmount = subtotal * (percent / 100);
        return subtotal - discountAmount;
    }
    
    @Override
	public String toString() {
		return "OrderDetail [orderDetailId=" + orderDetailId + ", order=" + order + ", product=" + product
				+ ", quantity=" + quantity + "]";
	}

}
