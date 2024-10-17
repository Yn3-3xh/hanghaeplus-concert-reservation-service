package hanghaeplus.domain.point.repository;

import hanghaeplus.domain.point.entity.PointHistory;

public interface PointHistoryRepository {

    void saveHistory(PointHistory pointHistory);
}
