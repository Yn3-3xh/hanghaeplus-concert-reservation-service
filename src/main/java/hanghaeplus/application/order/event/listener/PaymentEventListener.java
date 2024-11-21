package hanghaeplus.application.order.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghaeplus.application.order.event.producer.PaymentAlarmProducer;
import hanghaeplus.domain.order.entity.OrderOutbox;
import hanghaeplus.domain.order.event.PaymentEvent;
import hanghaeplus.domain.order.factory.OrderOutboxFactory;
import hanghaeplus.domain.order.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentAlarmProducer paymentAlarmProducer;
    private final ObjectMapper objectMapper;

    private final OrderOutboxRepository orderOutboxRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(PaymentEvent.Success event) {
        log.info("Init outbox");
        try {
            String message = this.getMessage(event);
            OrderOutbox orderOutbox = OrderOutboxFactory.createInit(event.transactionKey(), message);
            orderOutboxRepository.save(orderOutbox);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentEvent.Success event) {
        log.info("Listen payment success");
        String message = "";
        try {
            message = this.getMessage(event);
            paymentAlarmProducer.sendMessage(event.transactionKey(), message);
        } catch (Exception e) {
            OrderOutbox orderOutbox = OrderOutboxFactory.createFailedBefore(event.transactionKey(), message, e.getMessage());
            orderOutboxRepository.save(orderOutbox);
        }
    }

    private String getMessage(PaymentEvent.Success event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }
}
