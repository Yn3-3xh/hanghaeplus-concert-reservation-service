package hanghaeplus.application.point.service;

import hanghaeplus.domain.point.dto.PointCommand;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static hanghaeplus.application.point.error.PointErrorCode.NOT_FOUND_POINT;

@Service
@RequiredArgsConstructor
public class PointCommandService {

    private final PointRepository pointRepository;

    public void chargePoint(PointCommand.Create command) {
        Point point = pointRepository.findByUserId(command.userId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POINT.getMessage()));
        point.charge(command.amount());

        pointRepository.savePoint(point);
    }
}
