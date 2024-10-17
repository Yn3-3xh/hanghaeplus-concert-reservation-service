package hanghaeplus.domain.point.dto;

import hanghaeplus.domain.point.entity.enums.PointStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.point.error.PointErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("PointCommand 단위 테스트")
class PointCommandUnitTest {

    @Nested
    @DisplayName("PointCommand.Create 테스트")
    class CreateTests {

        @Test
        @DisplayName("PointCommand.Create 성공")
        void pass_createTest() {
            // given
            Long userId = 1L;
            int amount = 100;

            // when
            PointCommand.Create command = new PointCommand.Create(userId, amount);

            // then
            assertThat(command).isNotNull();
        }

        @Test
        @DisplayName("PointCommand.Create 실패 - userId가 null인 경우")
        void fail_createTest1() {
            // given
            Long userId = null;
            int amount = 100;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.Create(userId, amount);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
        }

        @Test
        @DisplayName("PointCommand.Create 실패 - userId가 0 이하인 경우")
        void fail_createTest2() {
            // given
            Long userId = 0L;
            int amount = 100;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.Create(userId, amount);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
        }

        @Test
        @DisplayName("PointCommand.Create 실패 - amount가 0 이하인 경우")
        void fail_createTest3() {
            // given
            Long userId = 1L;
            int amount = 0;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.Create(userId, amount);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_AMOUNT.getMessage());
        }
    }

    @Nested
    @DisplayName("PointCommand.CreatePointHistory 테스트")
    class CreatePointHistoryTests {

        @Test
        @DisplayName("PointCommand.CreatePointHistory 성공")
        void pass_createPointHistoryTest() {
            // given
            Long userId = 1L;
            int amount = 100;
            PointStatus status = PointStatus.CHARGE;

            // when
            PointCommand.CreatePointHistory command = new PointCommand.CreatePointHistory(userId, amount, status);

            // then
            assertThat(command).isNotNull();
        }

        @Test
        @DisplayName("PointCommand.CreatePointHistory 실패 - userId가 null인 경우")
        void fail_createPointHistoryTest1() {
            // given
            Long userId = null;
            int amount = 100;
            PointStatus status = PointStatus.CHARGE;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.CreatePointHistory(userId, amount, status);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
        }

        @Test
        @DisplayName("PointCommand.CreatePointHistory 실패 - userId가 0 이하인 경우")
        void fail_createPointHistoryTest2() {
            // given
            Long userId = 0L;
            int amount = 100;
            PointStatus status = PointStatus.CHARGE;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.CreatePointHistory(userId, amount, status);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
        }

        @Test
        @DisplayName("PointCommand.CreatePointHistory 실패 - amount가 0 이하인 경우")
        void fail_createPointHistoryTest3() {
            // given
            Long userId = 1L;
            int amount = 0;
            PointStatus status = PointStatus.CHARGE;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.CreatePointHistory(userId, amount, status);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_AMOUNT.getMessage());
        }

        @Test
        @DisplayName("PointCommand.CreatePointHistory 실패 - status가 null인 경우")
        void fail_createPointHistoryTest4() {
            // given
            Long userId = 1L;
            int amount = 100;
            PointStatus status = null;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new PointCommand.CreatePointHistory(userId, amount, status);
            });

            // then
            assertThat(exception.getMessage()).isEqualTo(INVALID_STATUS.getMessage());
        }
    }
}
