package hanghaeplus.domain.token.entity;

import hanghaeplus.domain.common.error.CoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static hanghaeplus.domain.token.entity.enums.TokenPolicy.TOKEN_EXPIRED_HOUR;
import static hanghaeplus.domain.token.error.TokenErrorCode.EXPIRED_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Token 단위 테스트")
class TokenUnitTest {

    @Test
    @DisplayName("Token checkExpired 성공 - 토큰 유효")
    void pass_checkExpiredTest() {
        // given
        String tokenId = "UUID";
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now().plusHours(TOKEN_EXPIRED_HOUR.getHour()).minusMinutes(1);
        Token sut = Token.create(tokenId, userId);

        // when & then
        assertDoesNotThrow(() -> sut.checkExpired(now));
    }

    @Test
    @DisplayName("Token checkExpired 실패 - 토큰 만료")
    void fail_checkExpiredTest() {
        // given
        String tokenId = "UUID";
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now().plusHours(TOKEN_EXPIRED_HOUR.getHour()).plusMinutes(1);
        Token token = Token.create(tokenId, userId);

        // when
        CoreException sut = assertThrows(CoreException.class, () -> token.checkExpired(now));

        // then
        assertThat(sut.getMessage()).isEqualTo(EXPIRED_TOKEN.getMessage());
    }
}