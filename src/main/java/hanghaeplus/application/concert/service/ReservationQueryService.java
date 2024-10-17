package hanghaeplus.application.concert.service;

import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_FOUND_AVAILABLE_RESERVATION;

@Component
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_AVAILABLE_RESERVATION.getMessage()));
    }
}
