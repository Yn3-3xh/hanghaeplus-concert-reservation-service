package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends CrudRepository<Seat, Long> {

    @Query("""
                SELECT s
                FROM Seat s
                WHERE s.concertDetailId = :detailId
                AND s.status = 'EMPTY'
            """)
    List<Seat> findAvailableSeatsByConcertDetailId(@Param("detailId") Long detailId);

    @Query("""
                SELECT s
                FROM Seat s
                WHERE s.id = :seatId
                AND s.status = 'EMPTY'
            """)
    Optional<Seat> findAvailableSeatById(@Param("seatId") Long seatId);

    @Query("""
                SELECT s
                FROM Seat s
                WHERE s.id = :seatId
                AND s.status = 'EMPTY'
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Seat> findAvailableSeatByIdLock(@Param("seatId") Long seatId);

    List<Seat> findByIdIn(List<Long> seatIds);

}
