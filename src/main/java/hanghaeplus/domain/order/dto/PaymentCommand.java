package hanghaeplus.domain.order.dto;

public class PaymentCommand {

    public record Create(
            Long userId,
            Long orderId,
            int amount
    ) {
    }
}
