package hanghaeplus.e2e.token;

import hanghaeplus.application.token.dto.TokenRequest;
import hanghaeplus.application.token.dto.TokenResponse;
import hanghaeplus.application.token.facade.TokenFacade;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnrollTokenTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate sut;

    @Autowired
    private TokenRepository tokenRepository;

    @MockBean
    private TokenFacade tokenFacade; // 파사드까지는 통합 테스트 했으므로 모킹해서 컨트롤러까지 e2e 테스트

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰 발급 테스트 - 통과")
    void pass_chargePointTest() {
        // given
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/tokens";
        String jsonRequestBody = "{\"userId\": 1}";
        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);

        when(tokenFacade.generateToken(new TokenRequest.EnrollToken(userId)))
                .thenReturn(new TokenResponse.EnrollToken(tokenId));

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
