package hanghaeplus.application.concert.service;

import hanghaeplus.application.concert.validator.SeatValidator;
import hanghaeplus.domain.concert.dto.ReservationCommand;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.entity.enums.ReservationStatus;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_FOUND_AVAILABLE_RESERVATION;

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

    public void updateReservationCompleted(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_AVAILABLE_RESERVATION.getMessage()));
        reservation.updateStatus(ReservationStatus.COMPLETED);

        reservationRepository.saveReservation(reservation);
    }
}
