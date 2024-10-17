package hanghaeplus.infra.concert.repository;

import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import hanghaeplus.infra.concert.jpa.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public void saveReservation(Reservation reservation) {
        reservationJpaRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId);
    }
}
