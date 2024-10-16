package hanghaeplus.application.concert.dto;

import java.time.LocalDate;
import java.util.List;

public class ConcertResponse {

    public record ConcertQueuePosition (
            int concertQueuePosition
    ) {

    }

    public record ConcertAvailableDates (
            List<LocalDate> concertAvailableDates
    ) {

    }

    public record ConcertAvailableSeats (
            Long seatId,
            String seatName,
            int price
    ) {

    }
}
