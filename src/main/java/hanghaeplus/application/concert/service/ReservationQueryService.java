package hanghaeplus.application.concert.service;

import hanghaeplus.application.concert.error.ConcertErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.concert.dto.ReservationQuery;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public Reservation getReservation(ReservationQuery.CreateReservation query) {
        return reservationRepository.findById(query.reservationId())
                .orElseThrow(() -> new CoreException(ConcertErrorCode.NOT_FOUND_AVAILABLE_RESERVATION));
    }

}
