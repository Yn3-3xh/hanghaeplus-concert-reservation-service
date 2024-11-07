package hanghaeplus.domain.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;

import java.util.List;
import java.util.Optional;

public interface QueueTokenRepository {

    int getWaitingPosition(String tokenId, Long queueId);

    void save(QueueToken queueToken);

    Optional<Integer> getExpiredActiveQueueTokenCount(Long queueId);

    List<QueueToken> popWaitingQueueToken(Long queueId, int waitingToActivatedCount);

    void insertActivatedQueueTokens(List<QueueToken> queueTokens);

    void deleteQueueToken(Long queueId, String tokenId);

    Optional<QueueToken> findWaitingQueueToken(Long queueId, String tokenId);

    Optional<QueueToken> findActiveQueueToken(Long queueId, String tokenId);
}
