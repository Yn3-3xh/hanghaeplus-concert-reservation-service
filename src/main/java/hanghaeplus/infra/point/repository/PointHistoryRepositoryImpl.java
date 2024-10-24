package hanghaeplus.infra.point.repository;

import hanghaeplus.domain.point.entity.PointHistory;
import hanghaeplus.domain.point.repository.PointHistoryRepository;
import hanghaeplus.infra.point.jpa.PointHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public void saveHistory(PointHistory pointHistory) {
        pointHistoryJpaRepository.save(pointHistory);
    }

    @Override
    public List<PointHistory> findByUserId(Long userId) {
        return pointHistoryJpaRepository.findByUserId(userId);
    }
}
