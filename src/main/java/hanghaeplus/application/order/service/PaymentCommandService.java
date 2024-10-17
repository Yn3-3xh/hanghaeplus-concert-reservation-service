package hanghaeplus.application.order.service;

import hanghaeplus.application.point.component.PointComponent;
import hanghaeplus.domain.order.dto.PaymentCommand;
import hanghaeplus.domain.order.entity.Payment;
import hanghaeplus.domain.order.entity.enums.PaymentType;
import hanghaeplus.domain.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;

    private final PointComponent pointComponent;

    public void executePayment(PaymentCommand.Create command) {
        pointComponent.checkAvailableWithdraw(command.userId(), command.amount());

        Payment payment = Payment.createPayed(command.userId(), command.orderId(), command.amount(), PaymentType.POINT);
        paymentRepository.savePayment(payment);

        pointComponent.withdrawPoint(command.userId(), command.amount());
    }
}
