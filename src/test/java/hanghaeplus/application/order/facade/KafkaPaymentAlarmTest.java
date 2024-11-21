package hanghaeplus.application.order.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghaeplus.application.order.event.consumer.PaymentAlarmConsumer;
import hanghaeplus.application.order.event.producer.PaymentAlarmProducer;
import hanghaeplus.domain.order.event.OrderTopicName;
import hanghaeplus.domain.order.event.PaymentEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class KafkaPaymentAlarmTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PaymentAlarmProducer paymentAlarmProducer;

    @SpyBean
    private PaymentAlarmConsumer paymentAlarmConsumer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[성공] 결제 알림 메시지 소비 테스트")
    void paymentAlarmMessageConsumedTest() {
        // given
        String topic = OrderTopicName.PAYMENT_ALARM_TOPIC;
        String message1, message2;

        try {
            message1 = objectMapper.writeValueAsString(new PaymentEvent.Success(1L, 1L, 1L));
            message2 = objectMapper.writeValueAsString(new PaymentEvent.Success(2L, 2L, 2L));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // when
        kafkaTemplate.send(topic, message1);
        paymentAlarmProducer.sendMessage(new PaymentEvent.SendMessage(message2));

        // then
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)                        // 최대 5초 대기
                .pollInterval(100, TimeUnit.MILLISECONDS)        // 100ms 간격으로 확인
                .until(() -> paymentAlarmConsumer.getConsumeCount() == 2); // 메시지 2개가 소비되었는지 확인

        // 소비 횟수 검증
        assertEquals(2, paymentAlarmConsumer.getConsumeCount());
        assertTrue(paymentAlarmConsumer.getConsumedMessages().contains(message1));
        assertTrue(paymentAlarmConsumer.getConsumedMessages().contains(message2));

        verify(paymentAlarmConsumer, times(2)).listenPaymentAlarm(any(ConsumerRecord.class));
    }
}