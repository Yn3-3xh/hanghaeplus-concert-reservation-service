package hanghaeplus.infra.point.jpa;

import hanghaeplus.domain.point.entity.Point;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PointJpaRepository extends CrudRepository<Point, Long> {

    Optional<Point> findByUserId(Long userId);
}
