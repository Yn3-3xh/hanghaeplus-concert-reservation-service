package hanghaeplus.infra.concert.repository;

import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.infra.concert.jpa.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> selectConcertAvailableSeats(Long detailId) {
        return seatJpaRepository.findAvailableSeatsByConcertDetailId(detailId);
    }

    @Override
    public Optional<Seat> findAvailableSeatById(Long seatId) {
        return seatJpaRepository.findAvailableSeatById(seatId);
    }

    @Override
    public Optional<Seat> findById(Long seatId) {
        return seatJpaRepository.findById(seatId);
    }

    @Override
    public void save(Seat seat) {
        seatJpaRepository.save(seat);
    }

    @Override
    public List<Seat> selectSeats(List<Long> seatIds) {
        return seatJpaRepository.findByIdIn(seatIds);
    }

    @Override
    public void saveSeats(List<Seat> seats) {
        seatJpaRepository.saveAll(seats);
    }
}
