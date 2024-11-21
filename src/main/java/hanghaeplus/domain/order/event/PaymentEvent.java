package hanghaeplus.domain.order.event;

public class PaymentEvent {

    public record Success(
            String transactionKey,
            Long orderId,
            Long userId,
            Long seatId
    ) {

    }

}
