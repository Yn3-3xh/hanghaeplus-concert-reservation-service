package hanghaeplus.domain.concert.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.concert.entity.enums.ConcertDetailStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "concert_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ConcertDetail extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertId;

    private String hall;

    private int seatLimitedCount;

    private LocalDate performedAt;

    private LocalDate reservationStartedAt;

    private LocalDate reservationEndedAt;

    private ConcertDetailStatus status;
}
