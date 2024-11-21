package hanghaeplus.infra.order.event;

import hanghaeplus.application.order.event.publisher.PaymentEventPublisher;
import hanghaeplus.domain.order.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisherImpl implements PaymentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void success(PaymentEvent.Success event) {
        applicationEventPublisher.publishEvent(event);
    }
}
