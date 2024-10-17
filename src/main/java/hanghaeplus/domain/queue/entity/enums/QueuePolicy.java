package hanghaeplus.domain.queue.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueuePolicy {
    WAITING_EXPIRED_MINUTE(60),
    ACTIVATED_EXPIRED_MINUTE(30),
    ;

    private final int minute;
}
