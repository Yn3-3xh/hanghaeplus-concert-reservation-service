package hanghaeplus.application.point.component;

import hanghaeplus.application.point.error.PointErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointQueryComponent {

    private final PointRepository pointRepository;

    public void checkAvailableWithdraw(Long userId, int amount) {
        pointRepository.findAvailableWithdraw(userId, amount)
                .orElseThrow(() -> new CoreException(PointErrorCode.INSUFFICIENT_POINTS));
    }

}
