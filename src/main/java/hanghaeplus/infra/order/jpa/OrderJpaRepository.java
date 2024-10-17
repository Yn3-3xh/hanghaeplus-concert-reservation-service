package hanghaeplus.infra.order.jpa;

import hanghaeplus.domain.order.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJpaRepository extends CrudRepository<Order, Long> {

    @Query("""
        SELECT o
        FROM Order o
        JOIN Reservation r
        ON o.reservationId = r.id
        WHERE o.id = :orderId
        AND r.status = 'PENDING'
    """)
    Optional<Order> findAvailableOrderById(@Param("orderId") Long orderId);
}
