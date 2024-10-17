package hanghaeplus.application.point.service;


import hanghaeplus.domain.point.dto.PointQuery;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static hanghaeplus.application.point.error.PointErrorCode.NOT_FOUND_POINT;

@Service
@RequiredArgsConstructor
public class PointQueryService {

    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public int selectPoint(PointQuery.Create query) {
        Point point = pointRepository.findByUserId(query.userId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POINT.getMessage()));

        return point.getPoint();
    }
}
