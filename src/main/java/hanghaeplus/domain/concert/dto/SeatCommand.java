package hanghaeplus.domain.concert.dto;

import java.util.List;

public class SeatCommand {

    public record CreateEmptySeats (
            List<Long> seatIds
    ) {

    }

    public record CreatePending(
            Long seatId
    ) {

    }
}
