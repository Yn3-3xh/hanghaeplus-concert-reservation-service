package hanghaeplus.domain.queue.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {

    INVALID_QUEUE_ID("유효하지 않은 대기열ID 입니다."),
    INVALID_QUEUE_TOKEN_ID("유효하지 않은 대기열 토큰ID 입니다."),
    INVALID_CONCERT_ID("유효하지 않은 콘서트ID 입니다."),
    INVALID_TOKEN_ID("토큰은 필수입니다."),
    EXPIRED_QUEUE_TOKEN("대기열 토큰이 만료되었습니다."),
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
