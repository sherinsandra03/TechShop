package hexa.org.entity;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	
	public Customer() {
		super();
	}
	
	public Customer(int customerId, String firstName, String lastName, String email, String phone, String address) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public Customer(String firstName, String lastName, String email, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastname(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int calculateTotalOrders() {
		return 0;
	}
	
	public void getCustomerDetails() {
		System.out.println("Firstname: "+firstName);
		System.out.println("Lastname: "+lastName);
		System.out.println("Email: "+email);
		System.out.println("Phone no: "+phone);
		System.out.println("Address: "+address);
	}
	
	public void updateCustomerInfo(String newEmail, String newPhone, String newAddress) {
		this.email = newEmail;
		this.phone =newPhone;
		this.address = newAddress;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", phone=" + phone + ", address=" + address + "]";
	}
	
	
}
