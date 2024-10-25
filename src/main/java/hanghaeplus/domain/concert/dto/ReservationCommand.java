package hanghaeplus.domain.concert.dto;

public class ReservationCommand {

    public record Create(
            Long seatId,
            Long userId
    ) {
    }

    public record CreateReservationCompleted(
            Long reservationId
    ) {

    }

}
