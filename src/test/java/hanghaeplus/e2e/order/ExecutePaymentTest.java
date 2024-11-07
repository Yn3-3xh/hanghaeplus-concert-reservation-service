package hanghaeplus.e2e.order;

import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.application.order.facade.OrderFacade;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openapi.model.PaymentHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExecutePaymentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate sut;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @MockBean
    private OrderFacade orderFacade; // 파사드까지는 통합 테스트 했으므로 모킹해서 컨트롤러까지 e2e 테스트

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
        queueRepository.deleteAll();
    }

    @Test
    @DisplayName("결제 테스트 - 통과")
    void pass_executePaymentTest() {
        // given
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        Queue queue = new Queue(null, 1L, 50);
        queueRepository.save(queue);

//        QueueToken queueToken = QueueToken.createWaiting(1L, tokenId);
//        queueTokenRepository.save(queueToken);

        QueueToken queueTokenActivated = QueueToken.create(1L, tokenId);
        queueTokenRepository.insertActivatedQueueTokens(List.of(queueTokenActivated));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.add("X-QUEUE-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

//        PaymentHttpRequest request = new PaymentHttpRequest(1L, 1L);
//        String url = "http://localhost:" + port + "/orders/payments";
//        HttpEntity<PaymentHttpRequest> entity = new HttpEntity<>(request, headers);

        String url = "http://localhost:" + port + "/orders/payments";
        String jsonRequestBody = "{\"orderId\": 1, \"concertId\": 1}";
        PaymentHttpRequest paymentHttpRequest = new PaymentHttpRequest(1L, 1L);
        HttpEntity<PaymentHttpRequest> entity = new HttpEntity<>(paymentHttpRequest, headers);

        doNothing().when(orderFacade).executePayment(new OrderRequest.paymentExecution(tokenId, 1L));

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
