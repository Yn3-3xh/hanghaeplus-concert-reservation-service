package hanghaeplus.domain.order.repository;

import hanghaeplus.domain.order.entity.OrderOutbox;
import hanghaeplus.domain.order.entity.enums.OrderOutboxStatus;

import java.util.List;
import java.util.Optional;

public interface OrderOutboxRepository {

    void save(OrderOutbox orderOutbox);

    Optional<OrderOutbox> findByTransactionKey(String transactionKey);

    List<OrderOutbox> findAllByStatus(OrderOutboxStatus status);
}
