package hanghaeplus.infra.queue.jpa;

import hanghaeplus.domain.queue.entity.QueueToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueueTokenJpaRepository extends CrudRepository<QueueToken, Long> {

    @Query("""
        SELECT COUNT(qt)
        FROM QueueToken qt
        WHERE qt.queueId = :queueId
        AND qt.createdAt <= (
            SELECT qt2.createdAt
            FROM QueueToken qt2
            WHERE qt2.tokenId = :tokenId
        )
    """)
    int getWaitingPosition(@Param("tokenId") String tokenId, @Param("queueId") Long queueId);

    Optional<QueueToken> findByTokenId(String tokenId);
}