package hanghaeplus.domain.order.dto;

import static hanghaeplus.domain.order.error.OrderErrorCode.INVALID_ORDER_ID;

public class OrderQuery {

    public record CreateAvailableOrder (
            Long orderID
    ) {
        public CreateAvailableOrder {
            if (orderID == null || orderID <= 0) {
                throw new IllegalArgumentException(INVALID_ORDER_ID.getMessage());
            }
        }
    }
}
