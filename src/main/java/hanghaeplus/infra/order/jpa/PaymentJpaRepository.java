package hanghaeplus.infra.order.jpa;

import hanghaeplus.domain.order.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentJpaRepository extends CrudRepository<Payment, Long> {

    List<Payment> findByUserId(Long userID);
}
