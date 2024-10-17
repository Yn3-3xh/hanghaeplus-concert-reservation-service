package hanghaeplus.domain.concert.dto;

public class ReservationCommand {

    public record Create(
            Long seatId,
            Long userId
    ) {
        public Create {
            if (seatId == null || seatId <= 0) {
                throw new IllegalArgumentException();
            }
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException();
            }
        }
    }
}
