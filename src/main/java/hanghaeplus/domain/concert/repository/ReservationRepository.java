package hanghaeplus.domain.concert.repository;

import hanghaeplus.domain.concert.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    void saveReservation(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);

    List<Reservation> selectExpiredPendingReservations();

    void saveReservations(List<Reservation> reservations);
}
