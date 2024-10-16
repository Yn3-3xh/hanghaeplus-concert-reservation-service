package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatJpaRepository extends CrudRepository<Seat, Long> {

    @Query("""
        SELECT s
        FROM Seat s
        WHERE s.concertDetailId = :detailId
        AND s.status = "EMPTY"
    """)
    List<Seat> findAvailableSeatsByConcertDetailId(@Param("detailId") Long detailId);
}
