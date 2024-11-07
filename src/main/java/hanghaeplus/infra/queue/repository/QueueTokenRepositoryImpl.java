package hanghaeplus.infra.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.infra.queue.redis.QueueTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

//    private final QueueTokenJpaRepository queueTokenJpaRepository;

    private final QueueTokenRedisRepository queueTokenRedisRepository;

    @Override
    public int getWaitingPosition(String tokenId, Long queueId) {
        return queueTokenRedisRepository.getWaitingPosition(tokenId, queueId);
    }

    @Override
    public void save(QueueToken queueToken) {
        queueTokenRedisRepository.save(queueToken);
    }

    @Override
    public Optional<Integer> getExpiredActiveQueueTokenCount(Long queueId) {
        return queueTokenRedisRepository.getExpiredActiveQueueTokenCount(queueId);
    }

    @Override
    public List<QueueToken> popWaitingQueueToken(Long queueId, int waitingToActivatedCount) {
        return queueTokenRedisRepository.popWaitingQueueToken(queueId, waitingToActivatedCount);
    }

    @Override
    public void insertActivatedQueueTokens(List<QueueToken> queueTokens) {
        queueTokenRedisRepository.insertActivatedQueueToken(queueTokens);
    }

    @Override
    public void deleteQueueToken(Long queueId, String tokenId) {
        queueTokenRedisRepository.deleteQueueToken(queueId, tokenId);
    }

    @Override
    public Optional<QueueToken> findWaitingQueueToken(Long queueId, String tokenId) {
        return queueTokenRedisRepository.findWaitingQueueToken(queueId, tokenId);
    }

    @Override
    public Optional<QueueToken> findActiveQueueToken(Long queueId, String tokenId) {
        return queueTokenRedisRepository.findActiveQueueToken(queueId, tokenId);
    }

}
