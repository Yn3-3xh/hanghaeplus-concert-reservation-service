package hanghaeplus.domain.order.event;

public class OrderCompletedEvent {

    public record Completed(
            Long orderId
    ) {

    }
}
