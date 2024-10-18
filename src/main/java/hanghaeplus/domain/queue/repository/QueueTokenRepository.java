package hanghaeplus.domain.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;

import java.util.List;
import java.util.Optional;

public interface QueueTokenRepository {

    int getWaitingPosition(String tokenId, Long queueId);

    void save(QueueToken queueToken);

    Optional<QueueToken> findByTokenId(String tokenId);

    List<QueueToken> selectExpiredActiveQueueTokens(Long queueId);

    List<QueueToken> selectSortedWaitingQueueTokens(Long queueId, int limit);

    void saveQueueTokens(List<QueueToken> activatedToExpiredQueueTokens);

    int getActivatedQueueTokenCount(Long queueId);

    List<QueueToken> findByQueueId(Long queueId);
}
