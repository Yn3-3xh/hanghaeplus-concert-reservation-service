package hanghaeplus.application.point.component;

import hanghaeplus.application.point.error.PointErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointCommandComponent {

    private final PointRepository pointRepository;

    public void withdrawPoint(Long userId, int amount) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new CoreException(PointErrorCode.NOT_FOUND_POINT));
        point.withdraw(amount);

        pointRepository.savePoint(point);
    }
}
