package hanghaeplus.domain.event;

public class OrderCompletedEvent {

    public record Completed(
            Long orderId
    ) {

    }
}
