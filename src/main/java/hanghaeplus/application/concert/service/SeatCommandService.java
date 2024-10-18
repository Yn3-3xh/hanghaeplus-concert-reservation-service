package hanghaeplus.application.concert.service;

import hanghaeplus.domain.concert.dto.SeatCommand;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_FOUND_SEAT;

@Component
@RequiredArgsConstructor
public class SeatCommandService {

    private final SeatRepository seatRepository;

    public void updateSeatCompleted(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_SEAT.getMessage()));
        seat.updateStatus(SeatStatus.RESERVATION);

        seatRepository.save(seat);
    }

    public void updateSeatsEmpty(SeatCommand.CreateEmptySeats command) {
        List<Seat> seats = seatRepository.selectSeats(command.seatIds()).stream()
                .map(seat -> {
                    seat.updateStatus(SeatStatus.EMPTY);
                    return seat;
                }).toList();
        seatRepository.saveSeats(seats);
    }

    public void pendConcertSeat(SeatCommand.CreatePending command) {
        Seat seat = seatRepository.findById(command.seatId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_SEAT.getMessage()));
        seat.updateStatus(SeatStatus.PENDING);

        seatRepository.save(seat);
    }
}
