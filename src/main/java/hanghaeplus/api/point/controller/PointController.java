package hanghaeplus.api.point.controller;

import hanghaeplus.application.point.dto.PointRequest;
import hanghaeplus.application.point.dto.PointResponse;
import hanghaeplus.application.point.facade.PointFacade;
import lombok.RequiredArgsConstructor;
import org.openapi.api.PointsApi;
import org.openapi.model.PointChargeHttpRequest;
import org.openapi.model.PointChargeHttpResponse;
import org.openapi.model.PointSelectHttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController implements PointsApi {

    private final PointFacade pointFacade;

    @Override
    public ResponseEntity<PointChargeHttpResponse> chargePoint(String tokenId, PointChargeHttpRequest pointChargeHttpRequest) {
        PointRequest.PointCharge request = new PointRequest.PointCharge(tokenId, pointChargeHttpRequest.getUserId(), pointChargeHttpRequest.getAmount());
        pointFacade.chargePoint(request);

        return ResponseEntity.ok(new PointChargeHttpResponse().message("포인트 충전이 완료되었습니다."));
    }

    @Override
    public ResponseEntity<PointSelectHttpResponse> selectPoint(String tokenId, Long userId) {
        PointRequest.PointSelection request = new PointRequest.PointSelection(tokenId, userId);
        PointResponse.PointSelection response = pointFacade.selectPoint(request);

        return ResponseEntity.ok(
                new PointSelectHttpResponse().point(response.point()));
    }
}
