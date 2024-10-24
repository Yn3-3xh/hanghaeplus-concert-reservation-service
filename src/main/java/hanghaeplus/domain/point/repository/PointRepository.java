package hanghaeplus.domain.point.repository;

import hanghaeplus.domain.point.entity.Point;

import java.util.Optional;

public interface PointRepository {

    Optional<Point> findByUserIdLock(Long userId);

    Optional<Point> findByUserId(Long userId);

    void savePoint(Point point);

    Optional<Point> findAvailableWithdraw(Long userId, int amount);

    void deleteAll();
}
