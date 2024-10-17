package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.ConcertDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ConcertDetailJpaRepository extends CrudRepository<ConcertDetail, Long> {

//    @Query("""
//        SELECT cd.performedAt
//        FROM ConcertDetail cd
//        WHERE cd.concertId = :concertId
//        AND cd.status = 'OPEN'
//        AND
//    """)
    @Query(value = """
        WITH RECURSIVE date_series AS (
            SELECT cd.reservation_started_at AS date
            FROM concert_detail cd
            WHERE cd.concert_id = :concertId AND cd.status = 'OPEN'
            UNION ALL
            SELECT DATE_ADD(date, INTERVAL 1 DAY)
            FROM date_series
            WHERE date < (
                SELECT cd.reservation_ended_at
                FROM concert_detail cd
                WHERE cd.concert_id = :concertId AND cd.status = 'OPEN'
                LIMIT 1
            )
        )
        SELECT date FROM date_series
    """, nativeQuery = true)
    List<Date> findAvailableDatesByConcertId(@Param("concertId") Long concertId);
}
