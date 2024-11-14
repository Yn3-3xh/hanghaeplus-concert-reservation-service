package hanghaeplus.application.concert.service;

import hanghaeplus.application.concert.error.ConcertErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.concert.dto.ReservationCommand;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import hanghaeplus.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_AVAILABLE_SEAT;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public void reserveConcertSeat(ReservationCommand.Create command) {
//        Seat seat = seatRepository.findAvailableSeatByIdWithPessimisticLock(command.seatId())
//        Seat seat = seatRepository.findAvailableSeatByIdWithOptimisticLock(command.seatId())
        Seat seat = seatRepository.findAvailableSeatById(command.seatId())
                .orElseThrow(() -> new CoreException(NOT_AVAILABLE_SEAT));

        Reservation reservation = Reservation.createPending(seat.getId(), command.userId());
        reservationRepository.saveReservation(reservation);
    }

    public void updateReservationCompleted(ReservationCommand.CreateReservationCompleted command) {
        Reservation reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new CoreException(ConcertErrorCode.NOT_FOUND_AVAILABLE_RESERVATION));
        reservation.updateCompleted();

        reservationRepository.saveReservation(reservation);
    }

    // 스케줄러에 사용할 메서드 - 임시 예약(+좌석) 만료
    @Transactional
    public void refreshExpiredReservations() {
        List<Reservation> expiredPendingReservations = reservationRepository.selectExpiredPendingReservations();
        if (expiredPendingReservations.isEmpty()) {
            // log 넣어줄까?
            return;
        }

        List<Reservation> reservations = expiredPendingReservations.stream()
                .map(reservation -> {
                    reservation.updateExpired();
                    return reservation;
                }).toList();
        reservationRepository.saveReservations(reservations);

        List<Long> seatIds = expiredPendingReservations.stream()
                .map(Reservation::getSeatId)
                .toList();
        List<Seat> seats = seatRepository.selectSeats(seatIds).stream()
                .peek(Seat::updateEmpty).toList();
        seatRepository.saveSeats(seats);
    }

}
