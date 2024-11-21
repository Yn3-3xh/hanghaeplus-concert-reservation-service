package hanghaeplus.infra.order.jpa;

import hanghaeplus.domain.order.entity.OrderOutbox;
import hanghaeplus.domain.order.entity.enums.OrderOutboxStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderOutboxJpaRepository extends CrudRepository<OrderOutbox, Long> {

    Optional<OrderOutbox> findByTransactionKey(String transactionKey);

    @Query("""
            SELECT o
            FROM OrderOutbox o
            WHERE o.status = :status
            """)
    List<OrderOutbox> findAllByStatus(@Param("status") OrderOutboxStatus status);
}
