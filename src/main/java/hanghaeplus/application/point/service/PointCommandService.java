package hanghaeplus.application.point.service;

import hanghaeplus.application.point.error.PointErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.point.dto.PointCommand;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointCommandService {

    private final PointRepository pointRepository;

    @Transactional
    public void chargePoint(PointCommand.Create command) {
        try {
            Point point = pointRepository.findByUserIdWithOptimisticLock(command.userId())
                    .orElseThrow(() -> new CoreException(PointErrorCode.NOT_FOUND_POINT));
            point.charge(command.amount());

            pointRepository.savePoint(point);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new CoreException(PointErrorCode.CONCURRENCY_POINT);
        }
    }
}
