package hanghaeplus.application.concert.component;

import hanghaeplus.domain.concert.dto.SeatCommand;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatCommandComponent {

    private final SeatRepository seatRepository;

    public void updateSeatsEmpty(SeatCommand.CreateEmptySeats command) {
        List<Seat> seats = seatRepository.selectSeats(command.seatIds()).stream()
                .peek(Seat::updateEmpty).toList();

        seatRepository.saveSeats(seats);
    }
}
