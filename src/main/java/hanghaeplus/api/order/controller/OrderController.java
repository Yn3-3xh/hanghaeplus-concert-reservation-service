package hanghaeplus.api.order.controller;

import org.openapi.api.OrderApi;
import org.openapi.model.PaymentHttpRequest;
import org.openapi.model.PaymentHttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController implements OrderApi {

    @Override
    public ResponseEntity<PaymentHttpResponse> executePayment(UUID X_USER_TOKEN, PaymentHttpRequest paymentHttpRequest) {
        return ResponseEntity.ok(new PaymentHttpResponse("결제가 완료되었습니다.", 1L));
    }
}
