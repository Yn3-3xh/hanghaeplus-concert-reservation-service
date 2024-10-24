package hanghaeplus.application.point.service;

import hanghaeplus.application.point.error.PointErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.point.dto.PointCommand;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointCommandService {

    private final PointRepository pointRepository;

    public void chargePoint(PointCommand.Create command) {
        Point point = pointRepository.findByUserIdLock(command.userId())
                .orElseThrow(() -> new CoreException(PointErrorCode.NOT_FOUND_POINT));
        point.charge(command.amount());

        pointRepository.savePoint(point);
    }
}
