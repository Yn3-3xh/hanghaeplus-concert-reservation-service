package hanghaeplus.domain.point.entity;

import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.point.error.PointErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private int point;

    @Version
    private Integer version;

    public void charge(int amount) {
        if (amount <= 0) {
            throw new CoreException(PointErrorCode.INVALID_AMOUNT);
        }

        this.point += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new CoreException(PointErrorCode.INVALID_AMOUNT);
        }
        if (point < amount) {
            throw new CoreException(PointErrorCode.INSUFFICIENT_POINTS);
        }

        this.point -= amount;
    }
}
