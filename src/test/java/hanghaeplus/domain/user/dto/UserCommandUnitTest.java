package hanghaeplus.domain.user.dto;

import hanghaeplus.domain.token.dto.TokenCommand;
import hanghaeplus.domain.token.error.TokenErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserCommand 유닛 테스트")
class UserCommandUnitTest {

    @Test
    @DisplayName("UserCommand.Create 성공")
    void pass_createTest() {
        // given
        Long userId = 1L;

        // when
        TokenCommand.Create createCommand = new TokenCommand.Create(userId);

        // then
        assertNotNull(createCommand);
    }

    @Test
    @DisplayName("UserCommand.Create 실패 - userId가 null인 경우")
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
    @DisplayName("UserCommand.Create 실패 - userId가 0 이하인 경우")
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