package hanghaeplus.domain.concert.dto;

import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_CONCERT_ID;

public class ConcertQuery {

    public record CreateConcertAvailableDates(
            Long concertId
    ) {
        public CreateConcertAvailableDates {
            if (concertId == null || concertId <= 0) {
                throw new IllegalArgumentException(INVALID_CONCERT_ID.getMessage());
            }
        }
    }
}
