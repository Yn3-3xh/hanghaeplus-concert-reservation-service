package hanghaeplus.domain.concert.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_CONCERT_DETAIL_ID;
import static hanghaeplus.domain.queue.error.QueueErrorCode.INVALID_QUEUE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SeatQuery 단위 테스트")
class SeatQueryUnitTest {

    @Test
    @DisplayName("SeatQuery.CreateConcertAvailableSeats 성공")
    void pass_createConcertAvailableSeatsTest() {
        // given
        Long detailId = 1L;

        // when
        SeatQuery.CreateConcertAvailableSeats query = new SeatQuery.CreateConcertAvailableSeats(detailId);

        // then
        assertThat(query).isNotNull();
    }

    @Test
    @DisplayName("SeatQuery.CreateConcertAvailableSeats 실패 - detailId가 null일 경우")
    void fail_createConcertAvailableSeatsTest1() {
        // given
        Long detailId = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new SeatQuery.CreateConcertAvailableSeats(detailId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_CONCERT_DETAIL_ID.getMessage());
    }

    @Test
    @DisplayName("SeatQuery.CreateConcertAvailableSeats 실패 - detailId가 0 이하일 경우")
    void fail_createConcertAvailableSeatsTest2() {
        // given
        Long detailId = 0L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new SeatQuery.CreateConcertAvailableSeats(detailId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_CONCERT_DETAIL_ID.getMessage());
    }
}