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
//        try {
//            Point point = pointRepository.findByUserIdWithOptimisticLock(userId)
        Point point = pointRepository.findByUserIdWithPessimisticLock(userId)
                .orElseThrow(() -> new CoreException(PointErrorCode.NOT_FOUND_POINT));
        point.withdraw(amount);

//        pointRepository.savePoint(point);
//        } catch (OptimisticEntityLockException e) {
//            throw new CoreException(PointErrorCode.CONCURRENCY_POINT);
//        }
    }
}
