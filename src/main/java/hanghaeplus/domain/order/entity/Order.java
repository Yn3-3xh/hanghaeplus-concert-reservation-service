package hanghaeplus.domain.order.entity;

import hanghaeplus.domain.order.entity.enums.OrderStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long reservationId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
