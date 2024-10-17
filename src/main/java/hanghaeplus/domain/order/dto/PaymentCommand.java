package hanghaeplus.domain.order.dto;

import static hanghaeplus.domain.order.error.OrderErrorCode.*;

public class PaymentCommand {

    public record Create (
            Long userId,
            Long orderId,
            int amount
    ) {
        public Create {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(INVALID_USER_ID.getMessage());
            }
            if (orderId == null || orderId <= 0) {
                throw new IllegalArgumentException(INVALID_ORDER_ID.getMessage());
            }
            if (amount <= 0) {
                throw new IllegalArgumentException(INVALID_AMOUNT.getMessage());
            }
        }
    }
}
