package hanghaeplus.application.concert.dto;

public class ConcertRequest {

    public record ConcertQueuePosition (
            String tokenId,
            Long concertId
    ) {

    }

    public record ConcertQueue (
            String tokenId,
            Long concertId
    ) {

    }

    public record ConcertQueueEnrollment (
            String tokenId,
            Long concertId
    ) {

    }

    public record ConcertAvailableDates (
            String tokenId,
            Long concertId
    ) {

    }

    public record ConcertAvailableSeats (
            String tokenId,
            Long concertId,
            Long detailId
    ) {

    }

    public record SeatReservation (
            String tokenId,
            Long concertId,
            Long detailId,
            Long seatId
    ) {

    }
}
