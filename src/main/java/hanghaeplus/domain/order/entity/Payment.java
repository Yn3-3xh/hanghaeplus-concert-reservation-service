package hanghaeplus.domain.order.entity;

import hanghaeplus.domain.order.entity.enums.PaymentStatus;
import hanghaeplus.domain.order.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long orderId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    public static Payment createPayed(
            Long userId,
            Long orderId,
            int amount,
            PaymentType type) {
        return new Payment(null, userId, orderId, amount, PaymentStatus.PAYED, type);
    }
}
