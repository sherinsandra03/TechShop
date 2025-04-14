package hexa.org.entity;

public class SalesReport {
	private String productName;
    private int totalQuantity;
    private double totalSales;
	public SalesReport() {
		super();
	}
	public SalesReport(String productName, int totalQuantity, double totalSales) {
		super();
		this.productName = productName;
		this.totalQuantity = totalQuantity;
		this.totalSales = totalSales;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	@Override
	public String toString() {
		return "SalesReport [productName=" + productName + ", totalQuantity=" + totalQuantity + ", totalSales="
				+ totalSales + "]";
	}
    
    

}
