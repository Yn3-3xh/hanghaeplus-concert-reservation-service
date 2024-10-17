package hanghaeplus.domain.concert.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.concert.entity.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static hanghaeplus.domain.concert.entity.enums.ConcertPolicy.RESERVATION_EXPIRED_MINUTE;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Reservation extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seatId;

    private Long userId;

    private Long orderId;

    private LocalDateTime expiredAt;

    private LocalDateTime reservedAt;

    private ReservationStatus status;

    public static Reservation createPending(Long seatId, Long userId) {
        return new Reservation(
                null,
                seatId,
                userId,
                null,
                LocalDateTime.now().plusMinutes(RESERVATION_EXPIRED_MINUTE.getMinute()),
                null,
                ReservationStatus.PENDING);
    }
}
