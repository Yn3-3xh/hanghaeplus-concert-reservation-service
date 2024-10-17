package hanghaeplus.domain.token.dto;

import hanghaeplus.domain.token.error.TokenErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenCommand 단위 테스트")
class TokenCommandUnitTest {

    @Test
    @DisplayName("TokenCommand.Create 성공")
    void pass_createTest() {
        // given
        Long userId = 1L;

        // when
        TokenCommand.Create command = new TokenCommand.Create(userId);

        // then
        assertNotNull(command);
    }

    @Test
    @DisplayName("TokenCommand.Create 실패 - userId가 null인 경우")
    void fail_createTest1() {
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
    @DisplayName("TokenCommand.Create 실패 - userId가 0 이하인 경우")
    void fail_createTest2() {
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