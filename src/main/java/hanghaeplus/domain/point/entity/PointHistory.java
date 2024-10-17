package hanghaeplus.domain.point.entity;

import hanghaeplus.domain.point.entity.enums.PointStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int amount;

    private PointStatus status;

    public static PointHistory create(Long userId, int amount, PointStatus status) {
        return new PointHistory(null, userId, amount, status);
    }
}
