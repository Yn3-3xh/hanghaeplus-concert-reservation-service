package hanghaeplus.api.point.controller;

import com.schooldevops.apifirst.openapi.domain.PointChargeRequest;
import com.schooldevops.apifirst.openapi.domain.PointChargeResponse;
import com.schooldevops.apifirst.openapi.domain.PointSelectResponse;
import com.schooldevops.apifirst.openapi.rest.PointsApi;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class PointController implements PointsApi {
    @Override
    public ResponseEntity<PointChargeResponse> chargePoint(UUID X_USER_TOKEN, PointChargeRequest pointChargeRequest) {
        return ResponseEntity.ok(new PointChargeResponse(25000));
    }

    @Override
    public ResponseEntity<PointSelectResponse> selectPoint(UUID X_USER_TOKEN, Integer userId) {
        return ResponseEntity.ok(new PointSelectResponse(25000));
    }
}
