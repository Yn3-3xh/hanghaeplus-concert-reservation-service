package hanghaeplus.infra.order.event;

import hanghaeplus.application.order.event.producer.PaymentAlarmProducer;
import hanghaeplus.domain.order.event.OrderTopicName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAlarmProducerImpl implements PaymentAlarmProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String key, String message) {
//        log.info("Send payment alarm event");

        log.info("key {}, message {}", key, message);
        kafkaTemplate.send(OrderTopicName.PAYMENT_ALARM_TOPIC, key, message);
    }
}
