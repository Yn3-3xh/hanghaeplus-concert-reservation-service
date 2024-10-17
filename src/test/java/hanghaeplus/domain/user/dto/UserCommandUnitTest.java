package hanghaeplus.domain.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.user.error.UserErrorCode.INVALID_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserCommand 유닛 테스트")
class UserCommandUnitTest {

    @Test
    @DisplayName("UserCommand.Create 성공")
    void pass_createTest() {
        // given
        Long userId = 1L;

        // when
        UserCommand.Create command = new UserCommand.Create(userId);

        // then
        assertThat(command).isNotNull();
    }

    @Test
    @DisplayName("UserCommand.Create 실패 - userId가 null인 경우")
    void fail_createTest1() {
        // given
        Long userId = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserCommand.Create(userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("UserCommand.Create 실패 - userId가 0 이하인 경우")
    void fail_createTest2() {
        // given
        Long userId = 0L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserCommand.Create(userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }
}