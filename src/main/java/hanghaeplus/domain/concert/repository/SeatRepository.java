package hanghaeplus.domain.concert.repository;

import hanghaeplus.domain.concert.entity.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> selectConcertAvailableSeats(Long detailId);

    Optional<Seat> findAvailableSeatById(Long seatId);
}
