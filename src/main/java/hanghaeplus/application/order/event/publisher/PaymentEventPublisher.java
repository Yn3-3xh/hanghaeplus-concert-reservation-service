package hanghaeplus.application.order.event.publisher;

import hanghaeplus.domain.order.event.PaymentEvent;

public interface PaymentEventPublisher {

    void success(PaymentEvent.Success event);
}
