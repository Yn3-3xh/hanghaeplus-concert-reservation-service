package hanghaeplus.application.order.event.producer;

public interface PaymentAlarmProducer {

    void sendMessage(String key, String message);
}
