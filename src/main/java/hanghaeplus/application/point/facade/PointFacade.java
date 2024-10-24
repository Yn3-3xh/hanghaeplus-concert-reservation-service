package hanghaeplus.application.point.facade;

import hanghaeplus.application.point.dto.PointRequest;
import hanghaeplus.application.point.dto.PointResponse;
import hanghaeplus.application.point.service.PointCommandService;
import hanghaeplus.application.point.service.PointHistoryCommandService;
import hanghaeplus.application.point.service.PointQueryService;
import hanghaeplus.application.token.service.TokenQueryService;
import hanghaeplus.domain.point.dto.PointCommand;
import hanghaeplus.domain.point.dto.PointQuery;
import hanghaeplus.domain.point.entity.enums.PointStatus;
import hanghaeplus.domain.token.dto.TokenQuery;
import hanghaeplus.domain.token.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PointFacade {

    private final TokenQueryService tokenQueryService;
    private final PointQueryService pointQueryService;

    private final PointCommandService pointCommandService;
    private final PointHistoryCommandService pointHistoryCommandService;

    public PointResponse.PointSelection selectPoint(PointRequest.PointSelection request) {
        Token token = tokenQueryService.getToken(new TokenQuery.Create(request.tokenId()));
        token.checkExpired(LocalDateTime.now());

        int point = pointQueryService.selectPoint(new PointQuery.Create(request.userId()));

        return new PointResponse.PointSelection(point);
    }

    @Transactional
    public void chargePoint(PointRequest.PointCharge request) {
        Token token = tokenQueryService.getToken(new TokenQuery.Create(request.tokenId()));
        token.checkExpired(LocalDateTime.now());

        pointCommandService.chargePoint(new PointCommand.Create(request.userId(), request.amount()));
        pointHistoryCommandService.insertHistory(new PointCommand.CreatePointHistory(request.userId(), request.amount(), PointStatus.CHARGE));
    }
}
