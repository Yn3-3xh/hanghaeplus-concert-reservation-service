package hanghaeplus.domain.order.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.order.entity.enums.OrderOutboxStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderOutbox extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionKey;

    private String message;

    @Enumerated(EnumType.STRING)
    private OrderOutboxStatus status;

    private String reason;

    private int failedCount;

    @Builder
    public OrderOutbox(String transactionKey,
                       String message,
                       OrderOutboxStatus status,
                       String reason,
                       int failedCount) {
        this.transactionKey = transactionKey;
        this.message = message;
        this.status = status;
        this.reason = reason;
        this.failedCount = failedCount;
    }

    public void published() {
        this.status = OrderOutboxStatus.PUBLISHED;
    }

    public void failed() {
        this.status = OrderOutboxStatus.FAILED;
    }

    public void increaseFailedCount() {
        this.failedCount++;
    }

}
