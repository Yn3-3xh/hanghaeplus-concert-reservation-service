package hanghaeplus.domain.order.repository;

import hanghaeplus.domain.order.entity.Payment;

public interface PaymentRepository {
    void savePayment(Payment payment);
}
