package hanghaeplus.domain.concert.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_CONCERT_ID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConcertQuery 단위 테스트")
class ConcertQueryUnitTest {

    @Test
    @DisplayName("ConcertQuery.CreateConcertAvailableDates 성공")
    void pass_createConcertAvailableDatesTest() {
        // given
        Long concertId = 1L;

        // when
        ConcertQuery.CreateConcertAvailableDates createConcertAvailableDates = new ConcertQuery.CreateConcertAvailableDates(concertId);

        // then
        assertNotNull(createConcertAvailableDates);
    }

    @Test
    @DisplayName("ConcertQuery.CreateConcertAvailableDates 실패 - concertId가 null일 경우")
    void fail_createConcertAvailableDatesTest1() {
        // given
        Long concertId = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ConcertQuery.CreateConcertAvailableDates(concertId);
        });

        // then
        assertEquals(INVALID_CONCERT_ID.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("ConcertQuery.CreateConcertAvailableDates 실패 - concertId가 0 이하일 경우")
    void fail_createConcertAvailableDatesTest2() {
        // given
        Long concertId = 0L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ConcertQuery.CreateConcertAvailableDates(concertId);
        });

        // then
        assertEquals(INVALID_CONCERT_ID.getMessage(), exception.getMessage());
    }
}