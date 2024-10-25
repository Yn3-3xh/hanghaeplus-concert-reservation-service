package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.Concert;
import org.springframework.data.repository.CrudRepository;

public interface ConcertJpaRepository extends CrudRepository<Concert, Long> {
}
