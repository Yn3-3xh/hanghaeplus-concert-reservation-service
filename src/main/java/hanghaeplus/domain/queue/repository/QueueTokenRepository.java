package hanghaeplus.domain.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;

public interface QueueTokenRepository {

    int getWaitingPosition(String tokenId, Long queueId);

    void save(QueueToken queueToken);
}
