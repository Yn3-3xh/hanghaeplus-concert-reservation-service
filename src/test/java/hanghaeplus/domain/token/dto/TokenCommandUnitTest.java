package hanghaeplus.domain.token.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.token.error.TokenErrorCode.INVALID_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(command).isNotNull();
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
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
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
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }
}