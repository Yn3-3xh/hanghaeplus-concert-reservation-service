package hanghaeplus.application.concert.component;

import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_AVAILABLE_SEAT;

@Component
@RequiredArgsConstructor
public class SeatQueryComponent {

    private final SeatRepository seatRepository;

    public Seat getAvailableSeat(Long seatId) {
        return seatRepository.findAvailableSeatById(seatId)
                .orElseThrow(() -> new CoreException(NOT_AVAILABLE_SEAT));
    }

    public Seat getAvailableSeatLock(Long seatId) {
        return seatRepository.findAvailableSeatByIdLock(seatId)
                .orElseThrow(() -> new CoreException(NOT_AVAILABLE_SEAT));
    }
}
