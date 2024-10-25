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
import org.springframework.web.client.ResourceAccessException;

import java.net.HttpRetryException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        queueTokenRepository.deleteAll();
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

        QueueToken queueToken = QueueToken.createWaiting(1L, tokenId);
        queueTokenRepository.save(queueToken);

        QueueToken queueTokenActivated = QueueToken.createActivated(1L, 1L, tokenId);
        queueTokenRepository.save(queueTokenActivated);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.add("X-QUEUE-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        PaymentHttpRequest request = new PaymentHttpRequest(1L);
        String url = "http://localhost:" + port + "/orders/payments";
        HttpEntity<PaymentHttpRequest> entity = new HttpEntity<>(request, headers);

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

    @Test
    @DisplayName("결제 테스트 - 실패 - 유저 토큰이 없는 경우")
    void fail_executePaymentTest1() {
        // given
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.add("X-QUEUE-TOKEN", null);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/orders/payments";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("결제 테스트 - 실패 - 대기열 토큰이 없는 경우")
    void fail_executePaymentTest2() {
        // given
        String token = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/orders/payments";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // when
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            sut.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class);
        });

        // then
        HttpRetryException clientError = (HttpRetryException) exception.getCause();
        assertThat(clientError.responseCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
