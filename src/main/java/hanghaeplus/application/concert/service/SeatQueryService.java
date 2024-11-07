package hanghaeplus.application.concert.service;

import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.concert.dto.SeatQuery;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_FOUND_SEAT;

@Service
@RequiredArgsConstructor
public class SeatQueryService {

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<Seat> selectConcertAvailableSeats(SeatQuery.CreateConcertAvailableSeats query) {
        return seatRepository.selectConcertAvailableSeats(query.detailId());
    }

    public Seat getSeat(SeatQuery.CreateSeat query) {
        return seatRepository.findById(query.seatId())
                .orElseThrow(() -> new CoreException(NOT_FOUND_SEAT));
    }
}
