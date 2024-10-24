package hanghaeplus.application.token.facade;

import hanghaeplus.application.token.dto.TokenRequest;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("TokenFacade 통합 테스트")
class TokenFacadeIntegrationTest {

    @Autowired
    private TokenFacade sut;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        tokenRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰 등록 테스트 - 통과")
    void pass_generateTokenTest() {
        // given
        Long userId = 1L;
        TokenRequest.EnrollToken request = new TokenRequest.EnrollToken(userId);

        // when
        sut.generateToken(request);

        // then
        Optional<Token> resultToken = tokenRepository.findByUserId(userId);
        assertThat(resultToken).isPresent();
    }
}
