package hanghaeplus.infra.point.repository;

import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import hanghaeplus.infra.point.jpa.PointJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Optional<Point> findByUserIdLock(Long userId) {
        return pointJpaRepository.findByUserIdLock(userId);
    }

    @Override
    public Optional<Point> findByUserId(Long userId) {
        return pointJpaRepository.findByUserId(userId);
    }

    @Override
    public void savePoint(Point point) {
        pointJpaRepository.save(point);
    }

    @Override
    public Optional<Point> findAvailableWithdraw(Long userId, int amount) {
        return pointJpaRepository.findAvailableWithdraw(userId, amount);
    }

    @Override
    public void deleteAll() {
        pointJpaRepository.deleteAll();
    }
}
