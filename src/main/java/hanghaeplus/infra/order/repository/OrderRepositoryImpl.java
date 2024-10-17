package hanghaeplus.infra.order.repository;

import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.repository.OrderRepository;
import hanghaeplus.infra.order.jpa.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Optional<Order> findAvailableOrderById(Long orderId) {
        return orderJpaRepository.findAvailableOrderById(orderId);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public void saveOrder(Order order) {
        orderJpaRepository.save(order);
    }
}
