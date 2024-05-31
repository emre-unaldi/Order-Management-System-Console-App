package unaldi.service;

import unaldi.model.Order;
import unaldi.repository.concretes.OrderRepository;

public class OrderService {
    private final OrderRepository orderRepository;
    private Long orderId = 0L;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(Order order) {
        order.setId(++orderId);
        orderRepository.save(order);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public void findAll() {
        orderRepository.findAll().forEach(System.out::println);
    }

    public Order findById(Long id) {
        return orderRepository.findAll().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
