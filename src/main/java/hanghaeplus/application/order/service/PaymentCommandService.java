package hanghaeplus.application.order.service;

import hanghaeplus.application.point.component.PointCommandComponent;
import hanghaeplus.application.point.component.PointQueryComponent;
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

    private final PointCommandComponent pointCommandComponent;
    private final PointQueryComponent pointQueryComponent;

    public void executePayment(PaymentCommand.Create command) {
        pointQueryComponent.checkAvailableWithdraw(command.userId(), command.amount());

        Payment payment = Payment.createPayed(command.userId(), command.orderId(), command.amount(), PaymentType.POINT);
        paymentRepository.savePayment(payment);

        pointCommandComponent.withdrawPoint(command.userId(), command.amount());
    }
}
