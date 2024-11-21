package hanghaeplus.domain.order.factory;

import hanghaeplus.domain.order.entity.OrderOutbox;
import hanghaeplus.domain.order.entity.enums.OrderOutboxStatus;

public class OrderOutboxFactory {

    public static OrderOutbox createInit(String transactionKey, String message) {
        return OrderOutbox.builder()
                .transactionKey(transactionKey)
                .message(message)
                .status(OrderOutboxStatus.INIT)
                .build();
    }

    public static OrderOutbox createFailedBefore(String transactionKey, String message, String reason) {
        return OrderOutbox.builder()
                .transactionKey(transactionKey)
                .message(message)
                .status(OrderOutboxStatus.FAILED)
                .reason(reason)
                .build();
    }
}
