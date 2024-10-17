package hanghaeplus.application.concert.validator;

import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_AVAILABLE_SEAT;

@Component
@RequiredArgsConstructor
public class SeatValidator {

    private final SeatRepository seatRepository;

    public void checkConcertAvailableSeat(Long seatId) {
        seatRepository.findAvailableSeatById(seatId)
                .orElseThrow(() -> new NoSuchElementException(NOT_AVAILABLE_SEAT.getMessage()));
    }
}
