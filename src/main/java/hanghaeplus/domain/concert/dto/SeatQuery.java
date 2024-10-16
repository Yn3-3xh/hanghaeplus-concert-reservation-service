package hanghaeplus.domain.concert.dto;

import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_CONCERT_DETAIL_ID;

public class SeatQuery {

    public record CreateConcertAvailableSeats(
            Long detailId
    ) {
        public CreateConcertAvailableSeats {
            if (detailId == null || detailId <= 0) {
                throw new IllegalArgumentException(INVALID_CONCERT_DETAIL_ID.getMessage());
            }
        }
    }
}
