package hanghaeplus.application.order.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghaeplus.application.order.event.consumer.PaymentAlarmConsumer;
import hanghaeplus.application.order.event.producer.PaymentAlarmProducer;
import hanghaeplus.domain.order.event.OrderTopicName;
import hanghaeplus.domain.order.event.PaymentEvent;
import hanghaeplus.domain.order.factory.OrderOutboxFactory;
import hanghaeplus.domain.order.repository.OrderOutboxRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
//@Import(TestKafkaConfig.class)
public class KafkaPaymentAlarmTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PaymentAlarmProducer paymentAlarmProducer;

    @SpyBean
    private PaymentAlarmConsumer paymentAlarmConsumer;

    @Autowired
    private OrderOutboxRepository orderOutboxRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[성공] 결제 알림 메시지 소비 테스트")
    void paymentAlarmMessageConsumedTest() throws JsonProcessingException {
        // given
        String topic = OrderTopicName.PAYMENT_ALARM_TOPIC;
        String transactionKey1 = UUID.randomUUID().toString();
        String transactionKey2 = UUID.randomUUID().toString();
        String message1 = objectMapper.writeValueAsString(new PaymentEvent.Success(transactionKey1, 1L, 1L, 1L));
        String message2 = objectMapper.writeValueAsString(new PaymentEvent.Success(transactionKey2, 2L, 2L, 2L));

        orderOutboxRepository.save(OrderOutboxFactory.createInit(transactionKey1, message1));
        orderOutboxRepository.save(OrderOutboxFactory.createInit(transactionKey2, message2));

        // when
        kafkaTemplate.send(topic, transactionKey1, message1);
        paymentAlarmProducer.sendMessage(transactionKey2, message2);

        // then
        await().atMost(ofSeconds(5))
                .untilAsserted(() -> {
                    assertThat(paymentAlarmConsumer.getConsumedMessage()).isEqualTo(message1);
                    assertThat(paymentAlarmConsumer.getConsumedMessage()).isEqualTo(message2);
                });

        verify(paymentAlarmConsumer, times(2)).listenPaymentAlarm(any(ConsumerRecord.class));
    }
}