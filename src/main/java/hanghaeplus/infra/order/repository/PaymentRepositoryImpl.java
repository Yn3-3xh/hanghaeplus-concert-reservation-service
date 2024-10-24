package hanghaeplus.infra.order.repository;

import hanghaeplus.domain.order.entity.Payment;
import hanghaeplus.domain.order.repository.PaymentRepository;
import hanghaeplus.infra.order.jpa.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void savePayment(Payment payment) {
        paymentJpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return paymentJpaRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findByUserId(Long userID) {
        return paymentJpaRepository.findByUserId(userID);
    }
}
