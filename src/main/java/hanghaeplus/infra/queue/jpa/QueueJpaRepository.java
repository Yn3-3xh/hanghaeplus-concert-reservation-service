package hanghaeplus.infra.queue.jpa;

import hanghaeplus.domain.queue.entity.Queue;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QueueJpaRepository extends CrudRepository<Queue, Long> {

    Optional<Queue> findByConcertId(Long concertId);
}
