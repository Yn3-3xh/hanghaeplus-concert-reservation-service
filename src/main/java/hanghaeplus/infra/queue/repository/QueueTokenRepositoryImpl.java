package hanghaeplus.infra.queue.repository;

import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.infra.queue.jpa.QueueTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
