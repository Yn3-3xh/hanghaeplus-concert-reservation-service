package hanghaeplus.application.order.event.producer;

import hanghaeplus.domain.order.event.PaymentEvent;

public interface PaymentAlarmProducer {

    void sendMessage(PaymentEvent.SendMessage message);
}
