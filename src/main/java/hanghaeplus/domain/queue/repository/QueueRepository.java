package hanghaeplus.domain.queue.repository;

import hanghaeplus.domain.queue.entity.Queue;

import java.util.List;
import java.util.Optional;

public interface QueueRepository {

    Optional<Queue> findByConcertId(Long concertId);

    void save(Queue queue);

    List<Queue> selectQueues();
}
