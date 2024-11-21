package hanghaeplus.application.order.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghaeplus.application.order.event.producer.PaymentAlarmProducer;
import hanghaeplus.domain.order.event.PaymentEvent;
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

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentEvent.Success event) {
        log.info("Listen payment success");
        try {
            paymentAlarmProducer.sendMessage(new PaymentEvent.SendMessage(this.getMessage(event)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMessage(PaymentEvent.Success event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }
}
