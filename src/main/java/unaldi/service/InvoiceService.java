package unaldi.service;

import unaldi.model.Invoice;
import unaldi.repository.concretes.InvoiceRepository;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private Long invoiceId = 0L;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
        this.initializeDataLoad();
    }

    public void save(Invoice invoice) {
        invoice.setId(++invoiceId);
        invoiceRepository.save(invoice);
    }

    public void delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    public void findAll() {
        invoiceRepository.findAll().forEach(System.out::println);
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findAll().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void filterHighValueInvoices(Double amount) {
        invoiceRepository.findAll().stream()
                .filter(invoice -> invoice.getAmount()!= null && invoice.getAmount() >= amount)
                .forEach(System.out::println);
    }

    public double calculateAverageOfHighValueInvoices() {
        List<Double> highValueInvoiceAmounts = invoiceRepository.findAll().stream()
                .filter(invoice -> invoice.getAmount()!= null && invoice.getAmount() >= 1500)
                .map(Invoice::getAmount)
                .toList();

        double totalAmount = highValueInvoiceAmounts.stream().mapToDouble(Double::doubleValue).sum();
        return totalAmount / highValueInvoiceAmounts.size();
    }

    private void initializeDataLoad() {
        this.save(new Invoice(LocalDateTime.now(), 4500.0));
        this.save(new Invoice(LocalDateTime.now(), 6000.0));
        this.save(new Invoice(LocalDateTime.now(), 7200.0));
    }
}
