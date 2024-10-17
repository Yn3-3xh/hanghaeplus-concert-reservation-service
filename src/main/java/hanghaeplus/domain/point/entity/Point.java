package hanghaeplus.domain.point.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static hanghaeplus.application.point.error.PointErrorCode.INSUFFICIENT_POINTS;
import static hanghaeplus.domain.point.error.PointErrorCode.INVALID_AMOUNT;

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

    public void charge(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT.getMessage());
        }

        this.point += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT.getMessage());
        }
        if (point < amount) {
            throw new IllegalArgumentException(INSUFFICIENT_POINTS.getMessage());
        }

        this.point -= amount;
    }
}
