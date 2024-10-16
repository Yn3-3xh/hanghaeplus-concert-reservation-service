package hanghaeplus.domain.concert.repository;

import hanghaeplus.domain.concert.entity.Seat;

import java.util.List;

public interface SeatRepository {

    List<Seat> selectConcertAvailableSeats(Long detailId);
}
