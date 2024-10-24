package hanghaeplus.infra.order.jpa;

import hanghaeplus.domain.order.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJpaRepository extends CrudRepository<Order, Long> {

    @Query(value = """
            SELECT o.*
            FROM orders o
            JOIN reservation r 
            ON o.reservation_id = r.id
            WHERE o.id = :orderId
            AND r.status = 'PENDING'
            """, nativeQuery = true)
    Optional<Order> findAvailableOrderById(@Param("orderId") Long orderId);
}
