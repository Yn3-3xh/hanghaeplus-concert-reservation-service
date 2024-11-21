package hanghaeplus.infra.order.repository;

import hanghaeplus.domain.order.entity.OrderOutbox;
import hanghaeplus.domain.order.entity.enums.OrderOutboxStatus;
import hanghaeplus.domain.order.repository.OrderOutboxRepository;
import hanghaeplus.infra.order.jpa.OrderOutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {

    private final OrderOutboxJpaRepository orderOutboxJpaRepository;

    @Override
    public void save(OrderOutbox orderOutbox) {
        orderOutboxJpaRepository.save(orderOutbox);
    }

    @Override
    public Optional<OrderOutbox> findByTransactionKey(String transactionKey) {
        return orderOutboxJpaRepository.findByTransactionKey(transactionKey);
    }

    @Override
    public List<OrderOutbox> findAllByStatus(OrderOutboxStatus status) {
        return orderOutboxJpaRepository.findAllByStatus(status);
    }
}
