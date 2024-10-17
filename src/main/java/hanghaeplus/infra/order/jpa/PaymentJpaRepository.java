package hanghaeplus.infra.order.jpa;

import hanghaeplus.domain.order.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentJpaRepository extends CrudRepository<Payment, Long> {
}
