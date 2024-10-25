package hanghaeplus.domain.order.repository;

import hanghaeplus.domain.order.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    void savePayment(Payment payment);

    Optional<Payment> findById(Long paymentId);

    List<Payment> findByUserId(Long userID);
}
