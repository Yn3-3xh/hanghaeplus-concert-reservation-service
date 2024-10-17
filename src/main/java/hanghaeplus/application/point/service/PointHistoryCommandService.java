package hanghaeplus.application.point.service;

import hanghaeplus.domain.point.dto.PointCommand;
import hanghaeplus.domain.point.entity.PointHistory;
import hanghaeplus.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryCommandService {

    private final PointHistoryRepository pointHistoryRepository;

    public void insertHistory(PointCommand.CreatePointHistory createPointHistory) {
        PointHistory pointHistory = PointHistory.create(
                createPointHistory.userId(), createPointHistory.amount(), createPointHistory.status());

        pointHistoryRepository.saveHistory(pointHistory);
    }
}
