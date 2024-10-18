package hanghaeplus.infra.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.infra.queue.jpa.QueueTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    @Override
    public int getWaitingPosition(String tokenId, Long queueId) {
        return queueTokenJpaRepository.getWaitingPosition(tokenId, queueId);
    }

    @Override
    public void save(QueueToken queueToken) {
        queueTokenJpaRepository.save(queueToken);
    }

    @Override
    public Optional<QueueToken> findByTokenId(String tokenId) {
        return queueTokenJpaRepository.findByTokenId(tokenId);
    }

    @Override
    public List<QueueToken> selectExpiredActiveQueueTokens(Long queueId) {
        return queueTokenJpaRepository.selectExpiredActiveQueueTokens(queueId);
    }

    @Override
    public List<QueueToken> selectSortedWaitingQueueTokens(Long queueId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return queueTokenJpaRepository.selectSortedWaitingQueueTokens(queueId, pageable);
    }

    @Override
    public void saveQueueTokens(List<QueueToken> activatedToExpiredQueueTokens) {
        queueTokenJpaRepository.saveAll(activatedToExpiredQueueTokens);
    }

    @Override
    public int getActivatedQueueTokenCount(Long queueId) {
        return queueTokenJpaRepository.getActivatedQueueTokenCount(queueId);
    }

    @Override
    public List<QueueToken> findByQueueId(Long queueId) {
        return queueTokenJpaRepository.findByQueueId(queueId);
    }

}
