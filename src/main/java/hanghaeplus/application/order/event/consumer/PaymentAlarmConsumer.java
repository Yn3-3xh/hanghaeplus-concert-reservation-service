package hanghaeplus.application.order.event.consumer;

import hanghaeplus.domain.order.event.OrderTopicName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAlarmConsumer {

    private final List<String> consumedMessages = new ArrayList<>();
    private int consumeCount = 0;

    @Transactional
    @KafkaListener(topics = OrderTopicName.PAYMENT_ALARM_TOPIC)
    public void listenPaymentAlarm(ConsumerRecord<String, String> record) {
        log.info("Notify payment success");
        log.debug("Record details: {}", record.toString());

        consumedMessages.add(record.value());
        consumeCount++;
    }

    public List<String> getConsumedMessages() {
        return consumedMessages;
    }

    public int getConsumeCount() {
        return consumeCount;
    }
}
