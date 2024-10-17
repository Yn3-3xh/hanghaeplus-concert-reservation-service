package hanghaeplus.domain.order.repository;

import hanghaeplus.domain.order.entity.Order;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findAvailableOrderById(Long orderId);

    Optional<Order> findById(Long orderId);

    void saveOrder(Order order);
}
