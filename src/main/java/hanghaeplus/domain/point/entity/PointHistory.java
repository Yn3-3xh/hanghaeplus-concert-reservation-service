package hanghaeplus.domain.point.entity;

import hanghaeplus.domain.point.entity.enums.PointStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private PointStatus status;

    public static PointHistory create(Long userId, int amount, PointStatus status) {
        return new PointHistory(null, userId, amount, status);
    }
}
