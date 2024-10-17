package hanghaeplus.infra.concert.jpa;

import hanghaeplus.domain.concert.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationJpaRepository extends CrudRepository<Reservation, Long> {
}
