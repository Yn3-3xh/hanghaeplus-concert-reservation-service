package hanghaeplus.infra.point.jpa;

import hanghaeplus.domain.point.entity.PointHistory;
import org.springframework.data.repository.CrudRepository;

public interface PointHistoryJpaRepository extends CrudRepository<PointHistory, Long> {
}
