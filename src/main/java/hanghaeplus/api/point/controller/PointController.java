package hanghaeplus.api.point.controller;

import org.openapi.api.PointsApi;
import org.openapi.model.PointChargeHttpRequest;
import org.openapi.model.PointChargeHttpResponse;
import org.openapi.model.PointSelectHttpResponse;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class PointController implements PointsApi {
    @Override
    public ResponseEntity<PointChargeHttpResponse> chargePoint(UUID X_USER_TOKEN, PointChargeHttpRequest pointChargeHttpRequest) {
        return ResponseEntity.ok(new PointChargeHttpResponse(BigDecimal.ZERO));
    }

    @Override
    public ResponseEntity<PointSelectHttpResponse> selectPoint(UUID X_USER_TOKEN, Long userId) {
        return ResponseEntity.ok(new PointSelectHttpResponse(BigDecimal.ZERO));
    }
}
