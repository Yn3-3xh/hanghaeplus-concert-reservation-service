package hanghaeplus.domain.concert.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConcertPolicy {
    RESERVATION_EXPIRED_MINUTE(5),
    ;

    private final int minute;
}