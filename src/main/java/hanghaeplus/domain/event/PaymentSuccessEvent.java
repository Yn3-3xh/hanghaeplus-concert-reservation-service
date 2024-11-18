package hanghaeplus.domain.event;

public class PaymentSuccessEvent {

    public record Success(
            Long orderId,
            Long userId,
            Long seatId
    ) {

    }
}
