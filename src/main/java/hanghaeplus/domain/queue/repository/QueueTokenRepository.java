package hanghaeplus.domain.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;

import java.util.Optional;

public interface QueueTokenRepository {

    int getWaitingPosition(String tokenId, Long queueId);

    void save(QueueToken queueToken);

    Optional<QueueToken> findByTokenId(String tokenId);
}
