package hanghaeplus.infra.point.jpa;

import hanghaeplus.domain.point.entity.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointJpaRepository extends CrudRepository<Point, Long> {

    Optional<Point> findByUserId(Long userId);

    @Query("""
        SELECT p
        FROM Point p
        WHERE p.userId = :userId
        AND p.point >= :amount
    """)
    Optional<Point> findAvailableWithdraw(@Param("userId") Long userId, @Param("amount") int amount);
}
