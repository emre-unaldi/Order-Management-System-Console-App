package unaldi;

import unaldi.model.Customer;
import unaldi.model.Invoice;
import unaldi.model.Order;
import unaldi.model.Product;
import unaldi.repository.concretes.CustomerRepository;
import unaldi.repository.concretes.InvoiceRepository;
import unaldi.repository.concretes.OrderRepository;
import unaldi.repository.concretes.ProductRepository;
import unaldi.service.CustomerService;
import unaldi.service.InvoiceService;
import unaldi.service.OrderService;
import unaldi.service.ProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService(new ProductRepository());
        InvoiceService invoiceService = new InvoiceService(new InvoiceRepository());
        OrderService orderService = new OrderService(new OrderRepository());
        CustomerService customerService = new CustomerService(new CustomerRepository());

        Scanner input = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            System.out.println("**************************************************************************************************");
            System.out.println("***************************** Welcome to Order Management System!!! ******************************");
            System.out.println("**************************************************************************************************");
            System.out.println("Which model do you want to perform the transaction on?");
            System.out.println("1 -> Product");
            System.out.println("2 -> Invoice");
            System.out.println("3 -> Order");
            System.out.println("4 -> Customer");
            System.out.println("**************************************************************************************************");
            System.out.println("0 -> Exit");
            System.out.println("**************************************************************************************************");
            int chosen = input.nextInt();

            switch (chosen) {
                case 1 -> productOperations(productService, input);
                case 2 -> invoiceOperations(invoiceService, input);
                case 3 -> orderOperations(orderService, productService, input);
                case 4 -> customerOperations(customerService, orderService,input);
                default -> isExit = true;
            }

            if (chosen == 0)
                isExit = true;
        }
    }

    private static void productOperations(ProductService productService, Scanner input) {
        boolean isBack = false;

        while (!isBack) {
            System.out.println("********************************************************************************");
            System.out.println("Please select action: ");
            System.out.println("0 -> Return to Main Menu");
            System.out.println("1 -> Add Product");
            System.out.println("2 -> Product Deletion");
            System.out.println("3 -> Fetch Product");
            System.out.println("4 -> Listing Products");
            System.out.println("********************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("********************************** Adding a Product *********************************");
                    System.out.println("Enter product name: ");
                    String name = input.next();
                    System.out.println("Enter product category:");
                    String category = input.next();
                    System.out.println("Enter the product price: ");
                    Double price = input.nextDouble();
                    System.out.println("Enter the product stock: ");
                    Integer stock = input.nextInt();

                    Product product = new Product(name, category, price, stock);
                    productService.save(product);
                    System.out.println("********************************************************************************");
                    break;
                case 2:
                    System.out.println("******************************* Product Deletion *******************************");
                    System.out.println("Please enter a product ID:");
                    Long deleteProductId = input.nextLong();

                    Product deleteProduct = productService.findById(deleteProductId);
                    productService.delete(deleteProduct);
                    System.out.println("********************************************************************************");
                    break;
                case 3:
                    System.out.println("********************************* Fetch Product ********************************");
                    System.out.println("Please enter a product ID:");
                    Long findProductId = input.nextLong();

                    Product foundProduct = productService.findById(findProductId);
                    System.out.println(foundProduct);
                    System.out.println("********************************************************************************");
                    break;
                case 4:
                    System.out.println("******************************* Listing Products *******************************");
                    productService.findAll();
                    System.out.println("********************************************************************************");
                    break;
                default:
                    isBack = true;
            }

            if (select == 0) isBack = true;
        }
    }

    private static void invoiceOperations(InvoiceService invoiceService, Scanner input) {
        boolean isBack = false;

        while (!isBack) {
            System.out.println("********************************************************************************");
            System.out.println("Please select action: ");
            System.out.println("0 -> Return to Main Menu");
            System.out.println("1 -> Add Invoice");
            System.out.println("2 -> Delete Invoice");
            System.out.println("3 -> Fetching Invoice");
            System.out.println("4 -> Listing Invoices");
            System.out.println("5 -> Listing Invoices Above 1500");
            System.out.println("6 -> Average of Over 1500 Invoices");
            System.out.println("********************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("********************************* Adding Invoice *******************************");
                    System.out.println("Fatura tutarını girin :");
                    Double amount = input.nextDouble();

                    Invoice invoice = new Invoice(LocalDateTime.now(), amount);
                    invoiceService.save(invoice);
                    System.out.println("********************************************************************************");
                    break;
                case 2:
                    System.out.println("********************************** Deleting Invoice *****************************");
                    System.out.println("Please enter an invoice id:");
                    Long deleteInvoiceId = input.nextLong();

                    Invoice deleteInvoice = invoiceService.findById(deleteInvoiceId);
                    invoiceService.delete(deleteInvoice);
                    System.out.println("********************************************************************************");
                    break;
                case 3:
                    System.out.println("****************************** Fetching Invoice ********************************");
                    System.out.println("Please enter an invoice id:");
                    Long findInvoiceId = input.nextLong();

                    Invoice foundInvoice = invoiceService.findById(findInvoiceId);
                    System.out.println(foundInvoice);
                    System.out.println("********************************************************************************");
                    break;
                case 4:
                    System.out.println("****************************** Listing Invoices ********************************");
                    invoiceService.findAll();
                    System.out.println("********************************************************************************");
                    break;
                case 5:
                    System.out.println("************************** Listing Invoices Above 1500 *************************");
                    invoiceService.filterHighValueInvoices(1500.0);
                    System.out.println("********************************************************************************");
                    break;
                case 6:
                    System.out.println("************************* Average of Over 1500 Invoices ************************");
                    System.out.println(invoiceService.calculateAverageOfHighValueInvoices());
                    System.out.println("********************************************************************************");
                    break;
                default:
                    isBack = true;
            }

            if (select == 0) {
                isBack = true;
            }
        }
    }

    private static void orderOperations(OrderService orderService, ProductService productService, Scanner input) {
        boolean isBack = false;

        while (!isBack) {
            System.out.println("********************************************************************************");
            System.out.println("Please select action: ");
            System.out.println("0 -> Return to Main Menu");
            System.out.println("1 -> Add Order");
            System.out.println("2 -> Delete Order");
            System.out.println("3 -> Order Fetch");
            System.out.println("4 -> Listing Orders");
            System.out.println("********************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("******************************** Adding an Order *******************************");
                    System.out.println("Enter customer number: ");
                    Long customerId = input.nextLong();
                    System.out.println("Enter the invoice number:");
                    Long invoiceId = input.nextLong();

                    boolean isContinue = false;
                    List<Product> productList = new ArrayList<>();

                    while (!isContinue) {
                        System.out.println("********************************************************************************");
                        productService.findAll();
                        System.out.println("********************************************************************************");
                        System.out.println("***************************** Adding Product List to Orders ********************");
                        System.out.println("Please select action: ");
                        System.out.println("1 -> Add Product");
                        System.out.println("0 -> Saving Added Products to Order");
                        System.out.println("********************************************************************************");

                        int chosen = input.nextInt();

                        if (chosen == 1) {
                            System.out.println("Enter the product ID you want to add:");
                            Long id = input.nextLong();
                            productList.add(productService.findById(id));
                        }

                        if (chosen == 0) {
                            isContinue = true;
                        }
                    }

                    Order order = new Order(customerId, invoiceId, productList);
                    orderService.save(order);
                    System.out.println("********************************************************************************");
                    break;
                case 2:
                    System.out.println("******************************** Order Deletion ********************************");
                    System.out.println("Please enter an order id:");
                    Long deleteOrderId = input.nextLong();

                    Order deleteOrder = orderService.findById(deleteOrderId);
                    orderService.delete(deleteOrder);
                    System.out.println("********************************************************************************");
                    break;
                case 3:
                    System.out.println("****************************** Order Fetch *******************************");
                    System.out.println("Please enter an order id:");
                    Long findOrderId = input.nextLong();

                    Order foundOrder = orderService.findById(findOrderId);
                    System.out.println(foundOrder);
                    System.out.println("********************************************************************************");
                    break;
                case 4:
                    System.out.println("******************************* Listing Orders *********************************");
                    orderService.findAll();
                    System.out.println("********************************************************************************");
                    break;
                default:
                    isBack = true;
            }

            if (select == 0) isBack = true;
        }
    }

    private static void customerOperations(CustomerService customerService, OrderService orderService, Scanner input) {
        boolean isBack = false;

        while (!isBack) {
            System.out.println("**************************************************************************************************");
            System.out.println("Please select action: ");
            System.out.println("0 -> Return to Main Menu");
            System.out.println("1 -> Add Customer");
            System.out.println("2 -> Delete Customer");
            System.out.println("3 -> Fetch Customer");
            System.out.println("4 -> Listing Customers");
            System.out.println("5 -> Listing the Customer's Full Name with the Letter C");
            System.out.println("6 -> Total Amounts of Invoices Created by Customers in June");
            System.out.println("7 -> Listing the Names of Customers whose Invoices Are Under 500 TL");
            System.out.println("**************************************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("**************************************** Adding a Customer ***************************************");
                    System.out.println("Enter customer name: ");
                    String firstName = input.next();
                    System.out.println("Enter customer surname: ");
                    String lastName = input.next();
                    System.out.println("Enter customer email: ");
                    String email = input.next();
                    System.out.println("Enter customer password: ");
                    String password = input.next();

                    boolean isContinue = false;
                    List<Order> orderList = new ArrayList<>();

                    while (!isContinue) {
                        System.out.println("**************************************************************************************************");
                        orderService.findAll();
                        System.out.println("**************************************************************************************************");
                        System.out.println("************************************ Adding Customer Orders **************************************");
                        System.out.println("Please select action: ");
                        System.out.println("1 -> Add Order");
                        System.out.println("0 -> Saving Added Orders to Customer");
                        System.out.println("**************************************************************************************************");

                        int chosen = input.nextInt();
                        input.nextLine();

                        if (chosen == 1) {
                            System.out.println("Enter the order id you want to add:");
                            Long id = input.nextLong();
                            orderList.add(orderService.findById(id));
                        }

                        if (chosen == 0) {
                            isContinue = true;
                        }
                    }

                    Customer customer = new Customer(firstName,lastName, email, password, LocalDate.now(), orderList);
                    customerService.save(customer);
                    System.out.println("**************************************************************************************************");
                    break;
                case 2:
                    System.out.println("**************************************** Customer Deletion ****************************************");
                    System.out.println("Please enter a customer id:");
                    Long deleteCustomerId = input.nextLong();

                    Customer deleteCustomer = customerService.findById(deleteCustomerId);
                    customerService.delete(deleteCustomer);
                    System.out.println("**************************************************************************************************");
                    break;
                case 3:
                    System.out.println("***************************************** Fetch Customer *****************************************");
                    System.out.println("Please enter a customer id:");
                    Long findCustomerId = input.nextLong();

                    Customer foundCustomer = customerService.findById(findCustomerId);
                    System.out.println(foundCustomer);
                    System.out.println("**************************************************************************************************");
                    break;
                case 4:
                    System.out.println("*************************************** Listing Customers ****************************************");
                    customerService.findAll();
                    System.out.println("**************************************************************************************************");
                    break;
                case 5:
                    System.out.println("************************ Listing Customer's Full Name with the Letter C **************************");
                    customerService.filterByFullNameContainingLetter("C");
                    System.out.println("**************************************************************************************************");
                    break;
                case 6:
                    System.out.println("********************* Total Amounts of Invoices Created by Customers in June *********************");
                    customerService.totalInvoiceAmountsForEnrolledInJune();
                    System.out.println("**************************************************************************************************");
                    break;
                case 7:
                    System.out.println("***************** Listing the Names of Customers whose Invoices Are Under 500 TL *****************");
                    customerService.filterNamesWithInvoicesUnderAmount(500.0);
                    System.out.println("**************************************************************************************************");
                    break;
                default:
                    isBack = true;
            }

            if (select == 0) isBack = true;
        }
    }
}