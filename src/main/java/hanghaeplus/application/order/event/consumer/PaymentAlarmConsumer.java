package hanghaeplus.application.order.event.consumer;

import hanghaeplus.application.order.error.OrderErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.order.entity.OrderOutbox;
import hanghaeplus.domain.order.event.OrderTopicName;
import hanghaeplus.domain.order.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAlarmConsumer {

    private final OrderOutboxRepository orderOutboxRepository;

    private String consumedMessages = "";

    //    @Transactional
    @KafkaListener(topics = OrderTopicName.PAYMENT_ALARM_TOPIC)
    public void listenPaymentAlarm(ConsumerRecord<String, String> record) {
        log.info("asd : " + record.key());
        log.info("asd : " + record.value());
        // key가 왜 null이 들어올까..

        OrderOutbox orderOutbox = orderOutboxRepository.findByTransactionKey(record.key())
                .orElseThrow(() -> new CoreException(OrderErrorCode.NOT_FOUND_OUTBOX));
        orderOutbox.published();
        orderOutboxRepository.save(orderOutbox);

        log.info("Notify payment success");
        log.debug("Record details: {}", record);

        consumedMessages = record.value();
    }

    public String getConsumedMessage() {
        return consumedMessages;
    }

}
