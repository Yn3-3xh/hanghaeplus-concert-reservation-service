package hanghaeplus.application.queue.error;

import hanghaeplus.application.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {

    NOT_FOUND_CONCERT_QUEUE("콘서트의 대기열이 존재하지 않습니다."),
    NOT_FOUND_QUEUE_TOKEN("대기열 토큰이 존재하지 않습니다."),
    ;

    private final String message;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
