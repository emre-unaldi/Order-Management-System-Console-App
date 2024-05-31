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
            System.out.println("**************************** Sipariş Yönetim Sistemine Hoşgeldiniz !!! ***************************");
            System.out.println("**************************************************************************************************");
            System.out.println("Hangi model üzerinden işlem gerçekleştirmek istiyorsunuz ?");
            System.out.println("1 -> Ürün");
            System.out.println("2 -> Fatura");
            System.out.println("3 -> Sipariş");
            System.out.println("4 -> Müşteri");
            System.out.println("**************************************************************************************************");
            System.out.println("0 -> Çıkış Yap");
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
            System.out.println("Lütfen işlem seçiniz : ");
            System.out.println("0 -> Ana Menüye Dön");
            System.out.println("1 -> Ürün Ekleme");
            System.out.println("2 -> Ürün Silme");
            System.out.println("3 -> Ürün Getirme");
            System.out.println("4 -> Ürünleri Listeleme");
            System.out.println("********************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("********************************** Ürün Ekleme *********************************");
                    System.out.println("Ürün adı girin : ");
                    String name = input.next();
                    System.out.println("Ürün kategorisi girin :");
                    String category = input.next();
                    System.out.println("Ürün fiyatını girin : ");
                    Double price = input.nextDouble();
                    System.out.println("Ürün stoğunu girin : ");
                    Integer stock = input.nextInt();

                    Product product = new Product(name, category, price, stock);
                    productService.save(product);
                    System.out.println("********************************************************************************");
                    break;
                case 2:
                    System.out.println("********************************** Ürün Silme **********************************");
                    System.out.println("Lütfen bir ürün id'si girin :");
                    Long deleteProductId = input.nextLong();

                    Product deleteProduct = productService.findById(deleteProductId);
                    productService.delete(deleteProduct);
                    System.out.println("********************************************************************************");
                    break;
                case 3:
                    System.out.println("********************************* Ürün Getirme *********************************");
                    System.out.println("Lütfen bir ürün id'si girin :");
                    Long findProductId = input.nextLong();

                    Product foundProduct = productService.findById(findProductId);
                    System.out.println(foundProduct);
                    System.out.println("********************************************************************************");
                    break;
                case 4:
                    System.out.println("****************************** Ürünleri Listeleme ******************************");
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
            System.out.println("Lütfen işlem seçiniz : ");
            System.out.println("0 -> Ana Menüye Dön");
            System.out.println("1 -> Fatura Ekleme");
            System.out.println("2 -> Fatura Silme");
            System.out.println("3 -> Fatura Getirme");
            System.out.println("4 -> Faturaları Listeleme");
            System.out.println("5 -> Faturalardan 1500 Üzerindekileri Listeleme");
            System.out.println("6 -> Faturalardan 1500 Üzerindeki Faturaların Ortalaması");
            System.out.println("********************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("********************************* Fatura Ekleme ********************************");
                    System.out.println("Fatura tutarını girin :");
                    Double amount = input.nextDouble();

                    Invoice invoice = new Invoice(LocalDateTime.now(), amount);
                    invoiceService.save(invoice);
                    System.out.println("********************************************************************************");
                    break;
                case 2:
                    System.out.println("********************************* Fatura Silme *********************************");
                    System.out.println("Lütfen bir fatura id'si girin :");
                    Long deleteInvoiceId = input.nextLong();

                    Invoice deleteInvoice = invoiceService.findById(deleteInvoiceId);
                    invoiceService.delete(deleteInvoice);
                    System.out.println("********************************************************************************");
                    break;
                case 3:
                    System.out.println("******************************** Fatura Getirme ********************************");
                    System.out.println("Lütfen bir fatura id'si girin :");
                    Long findInvoiceId = input.nextLong();

                    Invoice foundInvoice = invoiceService.findById(findInvoiceId);
                    System.out.println(foundInvoice);
                    System.out.println("********************************************************************************");
                    break;
                case 4:
                    System.out.println("***************************** Faturaları Listeleme *****************************");
                    invoiceService.findAll();
                    System.out.println("********************************************************************************");
                    break;
                case 5:
                    System.out.println("****************** Faturalardan 1500 Üzerindekileri Listeleme ******************");
                    invoiceService.filterHighValueInvoices(1500.0);
                    System.out.println("********************************************************************************");
                    break;
                case 6:
                    System.out.println("************** Faturalardan 1500 Üzerindeki Faturaların Ortalaması **************");
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
            System.out.println("Lütfen işlem seçiniz : ");
            System.out.println("0 -> Ana Menüye Dön");
            System.out.println("1 -> Sipariş Ekleme");
            System.out.println("2 -> Sipariş Silme");
            System.out.println("3 -> Sipariş Getirme");
            System.out.println("4 -> Siparişleri Listeleme");
            System.out.println("********************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("******************************** Sipariş Ekleme ********************************");
                    System.out.println("Müşteri numarasını girin : ");
                    Long customerId = input.nextLong();
                    System.out.println("Fatura numarasını girin :");
                    Long invoiceId = input.nextLong();

                    boolean isContinue = false;
                    List<Product> productList = new ArrayList<>();

                    while (!isContinue) {
                        System.out.println("********************************************************************************");
                        productService.findAll();
                        System.out.println("********************************************************************************");
                        System.out.println("*********************** Siparişlere Ürün Listesi Ekleme ************************");
                        System.out.println("Lütfen işlem seçiniz : ");
                        System.out.println("1 -> Ürün Ekleme");
                        System.out.println("0 -> Eklenen Ürünleri Siparişe Kaydetme");
                        System.out.println("********************************************************************************");

                        int chosen = input.nextInt();

                        if (chosen == 1) {
                            System.out.println("Eklemek istediğiniz ürün id'sini girin :");
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
                    System.out.println("******************************** Sipariş Silme *********************************");
                    System.out.println("Lütfen bir sipariş id'si girin :");
                    Long deleteOrderId = input.nextLong();

                    Order deleteOrder = orderService.findById(deleteOrderId);
                    orderService.delete(deleteOrder);
                    System.out.println("********************************************************************************");
                    break;
                case 3:
                    System.out.println("******************************* Sipariş Getirme ********************************");
                    System.out.println("Lütfen bir sipariş id'si girin :");
                    Long findOrderId = input.nextLong();

                    Order foundOrder = orderService.findById(findOrderId);
                    System.out.println(foundOrder);
                    System.out.println("********************************************************************************");
                    break;
                case 4:
                    System.out.println("**************************** Siparişleri Listeleme *****************************");
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
            System.out.println("Lütfen işlem seçiniz : ");
            System.out.println("0 -> Ana Menüye Dön");
            System.out.println("1 -> Müşteri Ekleme");
            System.out.println("2 -> Müşteri Silme");
            System.out.println("3 -> Müşteri Getirme");
            System.out.println("4 -> Müşterileri Listeleme");
            System.out.println("5 -> Müşterinin Tam Adında C Harfi Olanları Listeleme");
            System.out.println("6 -> Müşterilerden Haziran Ayında Oluşturulanların Faturalarının Toplam Tutarları");
            System.out.println("7 -> Müşterilerden Faturalarının 500 TL Altındakilerin İsimlerini Listeleme");
            System.out.println("**************************************************************************************************");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    System.out.println("***************************************** Müşteri Ekleme *****************************************");
                    System.out.println("Müşteri adını girin : ");
                    String firstName = input.next();
                    System.out.println("Müşteri soyadını girin : ");
                    String lastName = input.next();
                    System.out.println("Müşteri emailini girin : ");
                    String email = input.next();
                    System.out.println("Müşteri şifresini girin : ");
                    String password = input.next();

                    boolean isContinue = false;
                    List<Order> orderList = new ArrayList<>();

                    while (!isContinue) {
                        System.out.println("**************************************************************************************************");
                        orderService.findAll();
                        System.out.println("**************************************************************************************************");
                        System.out.println("********************************** Müşteri Siparişleri Ekleme ************************************");
                        System.out.println("Lütfen işlem seçiniz : ");
                        System.out.println("1 -> Sipariş Ekleme");
                        System.out.println("0 -> Eklenen Siparişleri Müşteriye Kaydetme");
                        System.out.println("**************************************************************************************************");

                        int chosen = input.nextInt();
                        input.nextLine();

                        if (chosen == 1) {
                            System.out.println("Eklemek istediğiniz sipariş id'sini girin :");
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
                    System.out.println("***************************************** Müşteri Silme ******************************************");
                    System.out.println("Lütfen bir müşteri id'si girin :");
                    Long deleteCustomerId = input.nextLong();

                    Customer deleteCustomer = customerService.findById(deleteCustomerId);
                    customerService.delete(deleteCustomer);
                    System.out.println("**************************************************************************************************");
                    break;
                case 3:
                    System.out.println("**************************************** Müşteri Getirme *****************************************");
                    System.out.println("Lütfen bir müşteri id'si girin :");
                    Long findCustomerId = input.nextLong();

                    Customer foundCustomer = customerService.findById(findCustomerId);
                    System.out.println(foundCustomer);
                    System.out.println("**************************************************************************************************");
                    break;
                case 4:
                    System.out.println("************************************* Müşterileri Listeleme **************************************");
                    customerService.findAll();
                    System.out.println("**************************************************************************************************");
                    break;
                case 5:
                    System.out.println("*********************** Müşterinin Tam Adında C Harfi Olanları Listeleme *************************");
                    customerService.filterByFullNameContainingLetter("C");
                    System.out.println("**************************************************************************************************");
                    break;
                case 6:
                    System.out.println("* Müşterilerden Haziran Ayında Oluşturulanların Faturalarının Toplam Tutarları *");
                    customerService.totalInvoiceAmountsForEnrolledInJune();
                    System.out.println("**************************************************************************************************");
                    break;
                case 7:
                    System.out.println("************* Müşterilerden Faturalarının 500 TL Altındakilerin İsimlerini Listeleme *************");
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