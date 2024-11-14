package hanghaeplus.application.event;

import hanghaeplus.domain.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void success(PaymentSuccessEvent.Success event) {
        applicationEventPublisher.publishEvent(event);
    }
}
