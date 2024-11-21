package hanghaeplus.application.order.event;

import hanghaeplus.application.order.event.producer.PaymentAlarmProducer;
import hanghaeplus.domain.order.entity.enums.OrderOutboxStatus;
import hanghaeplus.domain.order.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventRetryScheduler {

    private final int LIMIT_COUNT = 3;

    private final OrderOutboxRepository orderOutboxRepository;
    private final PaymentAlarmProducer paymentAlarmProducer;

    @Scheduled(fixedRate = 5000)
    void RetryPaymentEventPublisher() {
        orderOutboxRepository.findAllByStatus(OrderOutboxStatus.INIT).forEach(orderOutbox -> {
            if (orderOutbox.getFailedCount() <= LIMIT_COUNT) {
                orderOutbox.increaseFailedCount();
                LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(5);
                if (orderOutbox.getCreatedAt().isBefore(thresholdTime)) {
                    log.info("개발자에게 슬랙 알림");
                    paymentAlarmProducer.sendMessage(orderOutbox.getTransactionKey(), orderOutbox.getMessage());
                }
            } else {
                orderOutbox.failed();
                orderOutboxRepository.save(orderOutbox);
            }
        });
    }
}
