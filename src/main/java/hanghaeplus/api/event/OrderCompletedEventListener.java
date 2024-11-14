package hanghaeplus.api.event;

import hanghaeplus.application.concert.facade.ConcertFacade;
import hanghaeplus.domain.event.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderCompletedEventListener {

    private final ConcertFacade concertFacade;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCompleted(OrderCompletedEvent.Completed event) {
        concertFacade.completeReservation(event);
    }
}
