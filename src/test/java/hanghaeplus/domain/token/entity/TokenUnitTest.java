package hanghaeplus.domain.token.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static hanghaeplus.domain.token.error.TokenErrorCode.EXPIRED_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Token 단위 테스트")
class TokenUnitTest {

    @Test
    @DisplayName("Token checkExpired 성공 - 토큰 유효")
    void pass_checkExpiredTest() {
        // given
        String tokenId = "UUID";
        Long userId = 1L;
        LocalDateTime expiredAt = LocalDateTime.of(2024, 10, 18, 11, 0);
        Token token = Token.create(tokenId, userId, expiredAt);

        // when & then
        assertDoesNotThrow(token::checkExpired);
    }

    @Test
    @DisplayName("Token checkExpired 실패 - 토큰 만료")
    void fail_checkExpiredTest() {
        // given
        String tokenId = "UUID";
        Long userId = 1L;
        LocalDateTime expiredAt = LocalDateTime.of(2024, 10, 17, 11, 0);
        Token token = Token.create(tokenId, userId, expiredAt);

        // when
        IllegalStateException exception = assertThrows(IllegalStateException.class, token::checkExpired);

        // then
        assertThat(exception.getMessage()).isEqualTo(EXPIRED_TOKEN.getMessage());
    }
}