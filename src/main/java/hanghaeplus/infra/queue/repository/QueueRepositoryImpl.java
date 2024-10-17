package hanghaeplus.infra.queue.repository;

import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.infra.queue.jpa.QueueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Optional<Queue> findByConcertId(Long concertId) {
        return queueJpaRepository.findByConcertId(concertId);
    }

    @Override
    public void save(Queue queue) {
        queueJpaRepository.save(queue);
    }

    @Override
    public List<Queue> selectQueues() {
        return queueJpaRepository.findAll();
    }
}
