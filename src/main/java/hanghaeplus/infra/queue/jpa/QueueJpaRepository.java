package hanghaeplus.infra.queue.jpa;

import hanghaeplus.domain.queue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByConcertId(Long concertId);

}
