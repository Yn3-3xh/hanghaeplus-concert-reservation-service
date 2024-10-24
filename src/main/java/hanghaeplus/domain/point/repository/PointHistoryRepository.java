package hanghaeplus.domain.point.repository;

import hanghaeplus.domain.point.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepository {

    void saveHistory(PointHistory pointHistory);

    List<PointHistory> findByUserId(Long userId);

    void deleteAll();
}
