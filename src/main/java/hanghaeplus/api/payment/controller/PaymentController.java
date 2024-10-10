package hanghaeplus.api.payment.controller;

import com.schooldevops.apifirst.openapi.domain.PaymentRequest;
import com.schooldevops.apifirst.openapi.domain.PaymentResponse;
import com.schooldevops.apifirst.openapi.rest.PaymentsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController implements PaymentsApi {
    @Override
    public ResponseEntity<PaymentResponse> processPayment(UUID X_USER_TOKEN, PaymentRequest paymentRequest) {
        return ResponseEntity.ok(new PaymentResponse("결제가 완료되었습니다.", 1));
    }
}
