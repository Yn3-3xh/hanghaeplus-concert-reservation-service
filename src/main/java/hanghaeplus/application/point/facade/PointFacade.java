package hanghaeplus.application.point.facade;

import hanghaeplus.application.point.dto.PointRequest;
import hanghaeplus.application.point.dto.PointResponse;
import hanghaeplus.application.point.service.PointCommandService;
import hanghaeplus.application.point.service.PointHistoryCommandService;
import hanghaeplus.application.point.service.PointQueryService;
import hanghaeplus.domain.point.dto.PointCommand;
import hanghaeplus.domain.point.dto.PointQuery;
import hanghaeplus.domain.point.entity.enums.PointStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PointFacade {

    private final PointQueryService pointQueryService;
    private final PointCommandService pointCommandService;
    private final PointHistoryCommandService pointHistoryCommandService;

    public PointResponse.PointSelection selectPoint(PointRequest.PointSelection request) {
        int point = pointQueryService.selectPoint(
                new PointQuery.Create(request.userId()));

        return new PointResponse.PointSelection(point);
    }

    @Transactional
    public void chargePoint(PointRequest.PointCharge request) {
        pointCommandService.chargePoint(
                new PointCommand.Create(request.userId(), request.amount()));

        pointHistoryCommandService.insertHistory(
                new PointCommand.CreatePointHistory(request.userId(), request.amount(), PointStatus.CHARGE));
    }
}
