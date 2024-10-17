package hanghaeplus.infra.order.repository;

import hanghaeplus.domain.order.entity.Payment;
import hanghaeplus.domain.order.repository.PaymentRepository;
import hanghaeplus.infra.order.jpa.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void savePayment(Payment payment) {
        paymentJpaRepository.save(payment);
    }
}
