package hanghaeplus.application.order.dto;

public class OrderRequest {

    public record paymentExecution (
            String tokenId,
            Long orderId
    ) {

    }
}
