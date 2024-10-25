package hanghaeplus.infra.point.jpa;

import hanghaeplus.domain.point.entity.PointHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointHistoryJpaRepository extends CrudRepository<PointHistory, Long> {
    List<PointHistory> findByUserId(Long userId);
}
