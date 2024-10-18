package hanghaeplus.infra.queue.jpa;

import hanghaeplus.domain.queue.entity.QueueToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QueueTokenJpaRepository extends CrudRepository<QueueToken, Long> {

    @Query("""
        SELECT COUNT(qt)
        FROM QueueToken qt
        WHERE qt.queueId = :queueId
        AND qt.status = 'WAITING'
        AND qt.createdAt <= (
            SELECT qt2.createdAt
            FROM QueueToken qt2
            WHERE qt2.tokenId = :tokenId
        )
    """)
    int getWaitingPosition(@Param("tokenId") String tokenId, @Param("queueId") Long queueId);

    Optional<QueueToken> findByTokenId(String tokenId);

    @Query("""
        SELECT qt
        FROM QueueToken qt
        WHERE qt.queueId = :queueId
        AND qt.status = 'ACTIVATED'
        AND qt.expiredAt < CURRENT_TIMESTAMP
    """)
    List<QueueToken> selectExpiredActiveQueueTokens(@Param("queueId") Long queueId);

    @Query("""
        SELECT qt
        FROM QueueToken qt
        WHERE qt.queueId = :queueId
        AND qt.status = 'WAITING'
        ORDER BY qt.createdAt ASC
    """)
    List<QueueToken> selectSortedWaitingQueueTokens(@Param("queueId") Long queueId, Pageable pageable);

    @Query("""
        SELECT qt
        FROM QueueToken qt
        WHERE qt.queueId = :queueId
        AND qt.status = 'ACTIVATED'
    """)
    int getActivatedQueueTokenCount(@Param("queueId") Long queueId);

    List<QueueToken> findByQueueId(Long queueId);
}