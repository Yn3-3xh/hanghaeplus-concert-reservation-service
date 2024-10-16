package hanghaeplus.infra.concert.repository;

import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.infra.concert.jpa.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> selectConcertAvailableSeats(Long detailId) {
        return seatJpaRepository.findAvailableSeatsByConcertDetailId(detailId);
    }
}
