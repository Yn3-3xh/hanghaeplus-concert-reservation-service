package hanghaeplus.domain.concert.dto;

import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_SEAT_ID;
import static hanghaeplus.domain.concert.error.ConcertErrorCode.INVALID_USER_ID;

public class ReservationCommand {

    public record Create(
            Long seatId,
            Long userId
    ) {
        public Create {
            if (seatId == null || seatId <= 0) {
                throw new IllegalArgumentException(INVALID_SEAT_ID.getMessage());
            }
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(INVALID_USER_ID.getMessage());
            }
        }
    }
}
