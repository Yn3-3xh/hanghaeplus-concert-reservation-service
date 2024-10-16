package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.ConcertDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConcertDetailJpaRepository extends CrudRepository<ConcertDetail, Long> {

    @Query("""
        SELECT cd.performedAt
        FROM ConcertDetail cd
        WHERE cd.concertId = :concertId
        AND cd.status = 'OPEN'
    """)
    List<LocalDate> findAvailableDatesByConcertId(@Param("concertId") Long concertId);
}
