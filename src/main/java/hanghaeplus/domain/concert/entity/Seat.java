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

    private Long concertDetailId;

    private String name;

    private int price;

    private SeatStatus status;
}
