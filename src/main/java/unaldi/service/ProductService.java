package unaldi.service;

import unaldi.model.Product;
import unaldi.repository.concretes.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;
    private Long productId = 0L;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.initializeDataLoad();
    }

    public void save(Product product) {
        product.setId(++productId);
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void findAll() {
        productRepository.findAll().forEach(System.out::println);
    }

    public Product findById(Long id) {
        return productRepository.findAll().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void initializeDataLoad() {
        this.save(new Product("Monster Abra A5", "Notebook", 4500.0, 100));
        this.save(new Product("Samsung Galaxy A50", "Phone", 2500.0, 50));
        this.save(new Product("Logitech G213", "Keyboard", 1200.0, 40));
    }
}
