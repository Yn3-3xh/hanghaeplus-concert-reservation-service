package hanghaeplus.domain.queue.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueuePolicy {
    ACTIVATED_EXPIRED_MINUTE(30),
    ;

    private final int minute;
}
