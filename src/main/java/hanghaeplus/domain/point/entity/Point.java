package hanghaeplus.domain.point.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Long userId;

    private int point;

    public void charge(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT.getMessage());
        }

        this.point += amount;
    }
}
