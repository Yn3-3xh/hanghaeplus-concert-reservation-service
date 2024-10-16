package hanghaeplus.api.point.controller;

import org.openapi.api.PointsApi;
import org.openapi.model.PointChargeHttpRequest;
import org.openapi.model.PointChargeHttpResponse;
import org.openapi.model.PointSelectHttpResponse;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class PointController implements PointsApi {
    @Override
    public ResponseEntity<PointChargeHttpResponse> chargePoint(String tokenId, PointChargeHttpRequest pointChargeHttpRequest) {
        return ResponseEntity.ok(new PointChargeHttpResponse(BigDecimal.ZERO));
    }

    @Override
    public ResponseEntity<PointSelectHttpResponse> selectPoint(String tokenId, Long userId) {
        return ResponseEntity.ok(new PointSelectHttpResponse(BigDecimal.ZERO));
    }
}
