package hanghaeplus.application.point.component;

import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static hanghaeplus.application.point.error.PointErrorCode.INSUFFICIENT_POINTS;
import static hanghaeplus.application.point.error.PointErrorCode.NOT_FOUND_POINT;

@Component
@RequiredArgsConstructor
public class PointComponent {

    private final PointRepository pointRepository;

    public void checkAvailableWithdraw(Long userId, int amount) {
        pointRepository.findAvailableWithdraw(userId, amount)
                .orElseThrow(() -> new NoSuchElementException(INSUFFICIENT_POINTS.getMessage()));
    }

    public void withdrawPoint(Long userId, int amount) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POINT.getMessage()));
        point.withdraw(amount);

        pointRepository.savePoint(point);
    }
}
