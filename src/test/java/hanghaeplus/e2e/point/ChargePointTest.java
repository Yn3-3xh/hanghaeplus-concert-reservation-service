package hanghaeplus.e2e.point;

import hanghaeplus.application.point.dto.PointRequest;
import hanghaeplus.application.point.facade.PointFacade;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class ChargePointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate sut;

    @Autowired
    private TokenRepository tokenRepository;

    @MockBean
    private PointFacade pointFacade; // 파사드까지는 통합 테스트 했으므로 모킹해서 컨트롤러까지 e2e 테스트

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @Test
    @DisplayName("포인트 충전 테스트 - 통과")
    void pass_chargePointTest() {
        // given
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/points";
        String jsonRequestBody = "{\"userId\": 1, \"amount\": 100}";
        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);

        doNothing().when(pointFacade).chargePoint(new PointRequest.PointCharge(tokenId, userId, 10000));

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
    @DisplayName("포인트 충전 테스트 - 실패 - 유저 토큰이 없는 경우")
    void fail_chargePointTest() {
        // given
        String tokenId = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/points";
        String jsonRequestBody = "{\"userId\": 1, \"amount\": 100}";
        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);

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
