package hanghaeplus.api.order.controller;

import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.application.order.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.openapi.api.OrderApi;
import org.openapi.model.PaymentHttpRequest;
import org.openapi.model.PaymentHttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderFacade orderFacade;

    @Override
    public ResponseEntity<PaymentHttpResponse> executePayment(String tokenId, PaymentHttpRequest paymentHttpRequest) {
        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, paymentHttpRequest.getOrderId());
        orderFacade.executePayment(request);

        return ResponseEntity.ok(new PaymentHttpResponse("결제가 완료되었습니다."));
    }
}
