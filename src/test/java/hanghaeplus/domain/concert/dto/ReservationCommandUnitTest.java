package hanghaeplus.domain.concert.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_SEAT_ID;
import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReservationCommand 단위 테스트")
class ReservationCommandUnitTest {

    @Test
    @DisplayName("ReservationCommand.Create 성공")
    void pass_createTest() {
        // given
        Long seatId = 1L;
        Long userId = 1L;

        // when
        ReservationCommand.Create command = new ReservationCommand.Create(seatId, userId);

        // then
        assertThat(command).isNotNull();
    }

    @Test
    @DisplayName("ReservationCommand.Create 실패 - seatId가 null일 경우")
    void fail_createTest1() {
        // given
        Long seatId = null;
        Long userId = 1L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReservationCommand.Create(seatId, userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_SEAT_ID.getMessage());
    }

    @Test
    @DisplayName("ReservationCommand.Create 실패 - seatId가 0 이하일 경우")
    void fail_createTest2() {
        // given
        Long seatId = 0L;
        Long userId = 1L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReservationCommand.Create(seatId, userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_SEAT_ID.getMessage());
    }

    @Test
    @DisplayName("ReservationCommand.Create 실패 - userId가 null일 경우")
    void fail_createTest3() {
        // given
        Long seatId = 1L;
        Long userId = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReservationCommand.Create(seatId, userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("ReservationCommand.Create 실패 - userId가 0 이하일 경우")
    void fail_createTest4() {
        // given
        Long seatId = 1L;
        Long userId = 0L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReservationCommand.Create(seatId, userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }
}
