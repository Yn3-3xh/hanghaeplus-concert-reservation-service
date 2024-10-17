package hanghaeplus.application.concert.service;

import hanghaeplus.application.concert.validator.SeatValidator;
import hanghaeplus.domain.concert.dto.ReservationCommand;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final SeatValidator seatValidator;

    @Transactional
    public void reserveConcertSeat(ReservationCommand.Create command) {
        seatValidator.checkConcertAvailableSeat(command.seatId());

        Reservation reservation = Reservation.createPending(command.seatId(), command.userId());
        reservationRepository.saveReservation(reservation);
    }
}
