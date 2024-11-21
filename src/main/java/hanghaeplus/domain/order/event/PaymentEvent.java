package hanghaeplus.domain.order.event;

public class PaymentEvent {

    public record Success(
            Long orderId,
            Long userId,
            Long seatId
    ) {

    }

    public record SendMessage(
            String value
    ) {

    }
}
