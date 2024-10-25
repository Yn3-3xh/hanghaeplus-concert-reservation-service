package hanghaeplus.application.concert.service;

import hanghaeplus.application.concert.error.ConcertErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.concert.dto.SeatCommand;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatCommandService {

    private final SeatRepository seatRepository;

    public void updateSeatCompleted(SeatCommand.CreateSeatCompleted command) {
        Seat seat = seatRepository.findById(command.seatId())
                .orElseThrow(() -> new CoreException(ConcertErrorCode.NOT_FOUND_SEAT));
        seat.updateReservation();

        seatRepository.save(seat);
    }

    public void pendConcertSeat(SeatCommand.CreatePending command) {
        Seat seat = seatRepository.findById(command.seatId())
                .orElseThrow(() -> new CoreException(ConcertErrorCode.NOT_FOUND_SEAT));
        seat.updatePending();

        seatRepository.save(seat);
    }

}
