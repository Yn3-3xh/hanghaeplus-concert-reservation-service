package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationJpaRepository extends CrudRepository<Reservation, Long> {

    @Query("""
                SELECT r
                FROM Reservation r
                WHERE r.status = 'PENDING'
                AND r.expiredAt < CURRENT_TIMESTAMP
            """)
    List<Reservation> selectExpiredPendingReservations();

    @Query("""
                SELECT r
                FROM Reservation r
                WHERE r.seatId = :seatId
                AND r.status = 'PENDING'
            """)
    List<Reservation> findBySeatIdAndStatusPending(@Param("seatId") Long seatId);
}
