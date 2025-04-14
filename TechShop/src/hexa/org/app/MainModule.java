package hexa.org.app;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hexa.org.dao.ServiceProviderImpl;
import hexa.org.entity.Customer;
import hexa.org.entity.Inventory;
import hexa.org.entity.Order;
import hexa.org.entity.OrderDetail;
import hexa.org.entity.Payment;
import hexa.org.entity.Product;
import hexa.org.entity.SalesReport;
import hexa.org.exception.InsufficientStockException;
import hexa.org.exception.InvalidDataException;
import hexa.org.exception.PaymentFailedException;
import hexa.org.dao.CustomerDAOImpl;
import hexa.org.dao.InventoryDAOImpl;
import hexa.org.dao.OrderDAOImpl;
import hexa.org.dao.PaymentDAOImpl;
import hexa.org.dao.ProductDAOImpl;



public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceProviderImpl serviceProvider = new ServiceProviderImpl();
        boolean flag = true;

        while (flag) {
            System.out.println("Welcome to the TECHSHOP Management System!");
            System.out.println("Choose a category:");
            System.out.println("1. Customer Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
            System.out.println("4. Payment Management");
            System.out.println("5. Inventory Management");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customerManagement(scanner, serviceProvider);
                    break;
                case 2:
                    productManagement(scanner, serviceProvider);
                    break;
                case 3:
                    orderManagement(scanner, serviceProvider);
                    break;
                case 4:
                    paymentManagement(scanner, serviceProvider);
                    break;
                case 5:
                    inventoryManagement(scanner, serviceProvider);
                    break;
                case 6:
                    generateReport(scanner, serviceProvider);
                    break;
                case 7:
                    flag = false;
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }
    
 // Customer Management Methods
    public static void customerManagement(Scanner scanner, ServiceProviderImpl serviceProvider) {
        System.out.println("Customer Management:");
        System.out.println("1. Add Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Go Back");
        
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                System.out.println("Enter customer details: ");
                System.out.print("First Name: ");
                String firstName = scanner.nextLine();
                System.out.print("Last Name: ");
                String lastName = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Phone: ");
                String phone = scanner.nextLine();
                System.out.print("Address: ");
                String address = scanner.nextLine();

                Customer newCustomer = new Customer(firstName, lastName, email, phone, address);
                try {
                    customerDAO.registerCustomer(newCustomer);
                } catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                }
                break;
                
            case 2:
                System.out.println("Enter customer ID to update: ");
                int customerId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter new email: ");
                String newEmail = scanner.nextLine();
                System.out.println("Enter new phone: ");
                String newPhone = scanner.nextLine();

                try {
                    customerDAO.updateCustomerDetails(customerId, newEmail, newPhone);
                } catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
    
    // Product Management Methods
    public static void productManagement(Scanner scanner, ServiceProviderImpl serviceProvider) {
        System.out.println("Product Management:");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Update Product");
        System.out.println("4. Go Back");
        
        ProductDAOImpl productDAO = new ProductDAOImpl();

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
        case 1:
            System.out.println("Enter product details: ");
            System.out.print("Product Name: ");
            String productName = scanner.nextLine();
            System.out.print("Description: ");
            String description = scanner.nextLine();
            System.out.print("Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); 
            System.out.print("Category: ");
            String category = scanner.nextLine();

            Product newProduct = new Product(0, productName, description, price, category);  

            productDAO.addProductToDatabase(newProduct);
            break;

        case 2:
            System.out.println("Enter product ID to remove: ");
            int productIdToRemove = scanner.nextInt();

            productDAO.removeProductFromDatabase(productIdToRemove);
            break;
            case 3:
                System.out.println("Enter product ID to update: ");
                int productIdToUpdate = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter new product name: ");
                String updatedProductName = scanner.nextLine();
                System.out.println("Enter new description: ");
                String updatedDescription = scanner.nextLine();
                System.out.println("Enter new price: ");
                double updatedPrice = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Enter new category: ");
                String updatedCategory = scanner.nextLine();

                Product updatedProduct = new Product(productIdToUpdate, updatedProductName, updatedDescription, updatedPrice, updatedCategory);

                try {
                    productDAO.updateProduct(updatedProduct);
                } catch (InvalidDataException e) {
                    System.out.println("Error updating product: " + e.getMessage());
                }
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }    
    // Order Management Methods
    public static void orderManagement(Scanner scanner, ServiceProviderImpl serviceProvider) {
        System.out.println("Order Management:");
        System.out.println("1. Add Order");
        System.out.println("2. Cancel Order");
        System.out.println("3. Go Back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        OrderDAOImpl orderDAO = new OrderDAOImpl(); 
        CustomerDAOImpl customerDAO = new CustomerDAOImpl(); 
        ProductDAOImpl productDAO = new ProductDAOImpl(); 

        switch (choice) {
        case 1:
            System.out.print("Enter customer details:\nFirst Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();

            Customer newCustomer = new Customer(firstName, lastName, email, phone, address);

            try {
                customerDAO.registerCustomer(newCustomer); 
                int customerId = newCustomer.getCustomerId(); 
                System.out.println("Customer registered successfully with ID: " + customerId);

                System.out.print("Order Date (yyyy-mm-dd): ");
                String orderDateStr = scanner.nextLine();

                List<OrderDetail> orderDetails = new ArrayList<>();
                double totalAmount = 0.0; 

                System.out.print("Enter number of items in the order: ");
                int numItems = scanner.nextInt();
                scanner.nextLine(); 
                for (int i = 0; i < numItems; i++) {
                    System.out.println("Enter details for item " + (i + 1));
                    System.out.print("Product ID: ");
                    int productId = scanner.nextInt();

                    Product product = productDAO.getProductById(productId);  
                    if (product == null) {
                        System.out.println("Product not found for ID: " + productId);
                        continue; 
                    }

                    System.out.print("Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); 

                    OrderDetail orderDetail = new OrderDetail(product, quantity);

                    orderDetails.add(orderDetail);
                    totalAmount += product.getPrice() * quantity;  
                }

                String orderStatus = "Pending"; 

                Order newOrder = new Order(0, newCustomer, LocalDateTime.parse(orderDateStr + "T00:00:00"), totalAmount, orderStatus);
                newOrder.setOrderDetails(orderDetails); 
                try {
                    orderDAO.addOrder(newOrder); 
                    System.out.println("Order placed successfully!");
                } catch (InsufficientStockException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (InvalidDataException e) {
                System.out.println("Error: " + e.getMessage());
                return;  
            }
            break;


            case 2:
                System.out.print("Enter order ID to cancel: ");
                int orderIdToCancel = scanner.nextInt();
                orderDAO.cancelOrder(orderIdToCancel);
                System.out.println("Order canceled successfully!");
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    // Payment Management Methods
    public static void paymentManagement(Scanner scanner, ServiceProviderImpl serviceProvider) {
        System.out.println("Payment Management:");
        System.out.println("1. Record Payment");
        System.out.println("2. Update Payment Status");
        System.out.println("3. Go Back");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        PaymentDAOImpl paymentDAO = new PaymentDAOImpl(); 

        switch (choice) {
            case 1:
                System.out.println("Enter payment details:");
                System.out.print("Order ID: ");
                int orderId = scanner.nextInt();
                System.out.print("Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); 
                System.out.print("Payment Status (e.g., Completed, Failed): ");
                String paymentStatus = scanner.nextLine();

                Payment payment = new Payment(0, orderId, amount, paymentStatus);

                try {
                    paymentDAO.recordPayment(payment); 
                } catch (InvalidDataException | PaymentFailedException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                System.out.println("Enter payment ID to update: ");
                int paymentId = scanner.nextInt();
                scanner.nextLine(); 
                System.out.println("Enter new payment status (e.g., Completed, Failed): ");
                String newPaymentStatus = scanner.nextLine();

                try {
                    paymentDAO.updatePaymentStatus(paymentId, newPaymentStatus); 
                } catch (SQLException e) {
                    System.out.println("Error updating payment status: " + e.getMessage());
                }
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
    
    // Inventory Management Methods
    public static void inventoryManagement(Scanner scanner, ServiceProviderImpl serviceProvider) {
        System.out.println("Inventory Management:");
        System.out.println("1. Add Inventory");
        System.out.println("2. Remove Inventory");
        System.out.println("3. Update Inventory");
        System.out.println("4. Go Back");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        InventoryDAOImpl inventoryDAO = new InventoryDAOImpl(); 

        switch (choice) {
        case 1:
        	System.out.println("Enter product details:");
        	System.out.print("Product ID: ");
        	int productId = scanner.nextInt();
        	System.out.print("Quantity in Stock: ");
        	int quantityInStock = scanner.nextInt();
        	scanner.nextLine(); 

        	Product product = new Product(productId, "Default Product Name", "Default Description", 0.0, "Default Category");
        	LocalDateTime currentTime = LocalDateTime.now();  
        	String lastStockUpdateStr = scanner.nextLine(); 

        	LocalDateTime lastStockUpdate = (lastStockUpdateStr != null && !lastStockUpdateStr.trim().isEmpty()) 
        	    ? LocalDateTime.parse(lastStockUpdateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        	    : currentTime; 

        	Inventory inventory = new Inventory(0, product, quantityInStock, lastStockUpdate); 
        	inventoryDAO.addInventory(inventory); 
        	break;

            case 2:
                System.out.println("Enter product ID to remove inventory: ");
                int productIdToRemove = scanner.nextInt();
                inventoryDAO.removeInventory(productIdToRemove); // Remove inventory for the given product ID
                break;

            case 3:
                System.out.println("Enter product ID to update inventory: ");
                int productIdToUpdate = scanner.nextInt();
                System.out.println("Enter new quantity: ");
                int newQuantity = scanner.nextInt();

                inventoryDAO.updateInventory(productIdToUpdate, newQuantity);
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
    
    // Report Generation Methods
    public static void generateReport(Scanner scanner, ServiceProviderImpl serviceProvider) {
        System.out.println("Generate Report:");
        System.out.println("1. Sales Report by Product");
        System.out.println("2. Go Back");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Generating Sales Report...");
                
                List<SalesReport> salesReports = serviceProvider.getSalesByProduct();
                
                if (salesReports != null && !salesReports.isEmpty()) {
                    System.out.println("Sales Report by Product:");
                    System.out.println("Product Name | Total Quantity Sold | Total Sales");
                    for (SalesReport report : salesReports) {
                        System.out.println(report.getProductName() + " | " + report.getTotalQuantity() + " | " + report.getTotalSales());
                    }
                } else {
                    System.out.println("No sales data available.");
                }
                break;
                
            case 2:
            	break;
                
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
    
 }

       

    
    
    
    
