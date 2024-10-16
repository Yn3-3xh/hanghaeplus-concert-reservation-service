package hanghaeplus.domain.token.dto;

import hanghaeplus.domain.token.error.TokenErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenCommand 단위 테스트")
class TokenCommandUnitTest {

    @Test
    @DisplayName("TokenCommand.Create 성공")
    void createTokenCommandTest() {
        // given
        Long userId = 1L;

        // when
        TokenCommand.Create createCommand = new TokenCommand.Create(userId);

        // then
        assertNotNull(createCommand);
    }

    @Test
    @DisplayName("TokenCommand.Create 실패 - userId가 null일 경우")
    void fail_createTokenCommandTest1() {
        // given
        Long userId = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TokenCommand.Create(userId);
        });

        // then
        assertEquals(TokenErrorCode.INVALID_USER_ID.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("TokenCommand.Create 실패 - userId가 0 이하 일 경우")
    void fail_createTokenCommandTest2() {
        // given
        Long userId = 0L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TokenCommand.Create(userId);
        });

        // then
        assertEquals(TokenErrorCode.INVALID_USER_ID.getMessage(), exception.getMessage());
    }
}