package hanghaeplus.application.point.service;


import hanghaeplus.application.point.error.PointErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.point.dto.PointQuery;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointQueryService {

    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public int selectPoint(PointQuery.Create query) {
        Point point = pointRepository.findByUserId(query.userId())
                .orElseThrow(() -> new CoreException(PointErrorCode.NOT_FOUND_POINT));

        return point.getPoint();
    }
}
