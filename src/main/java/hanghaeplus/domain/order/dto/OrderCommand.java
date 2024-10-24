package hanghaeplus.domain.order.dto;

public class OrderCommand {

    public record CreateOrderCompleted(
            Long orderId
    ) {

    }
}
