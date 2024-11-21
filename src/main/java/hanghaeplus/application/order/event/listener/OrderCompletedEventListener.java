package hanghaeplus.application.order.event.listener;

import hanghaeplus.application.concert.facade.ConcertFacade;
import hanghaeplus.domain.order.event.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCompletedEventListener {

    private final ConcertFacade concertFacade;

    //    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    public void handleOrderCompleted(OrderCompletedEvent.Completed event) {
        concertFacade.completeReservation(event);
    }
}
