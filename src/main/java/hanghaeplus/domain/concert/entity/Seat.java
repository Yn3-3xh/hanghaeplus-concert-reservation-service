package hanghaeplus.domain.concert.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Seat extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long concertDetailId;

    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    public void updateStatus(SeatStatus status) {
        this.status = status;
    }
}
