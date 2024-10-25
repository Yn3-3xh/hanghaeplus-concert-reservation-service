package hanghaeplus.domain.queue.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.BAD_REQUEST;
import static hanghaeplus.domain.common.error.ErrorStatus.CONFLICT;

@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {

    INVALID_QUEUE_ID(BAD_REQUEST, "유효하지 않은 대기열ID 입니다.", LogLevel.WARN),
    INVALID_QUEUE_TOKEN_ID(BAD_REQUEST, "유효하지 않은 대기열 토큰ID 입니다.", LogLevel.WARN),
    INVALID_CONCERT_ID(BAD_REQUEST, "유효하지 않은 콘서트ID 입니다.", LogLevel.WARN),
    INVALID_TOKEN_ID(BAD_REQUEST, "토큰은 필수입니다.", LogLevel.WARN),
    EXPIRED_QUEUE_TOKEN(CONFLICT, "대기열 토큰이 만료되었습니다.", LogLevel.INFO),
    NOT_USED_QUEUE_TOKEN(CONFLICT, "사용할 수 없는 대기열 토큰입니다.", LogLevel.INFO),
    ;

    private final ErrorStatus status;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorStatus getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public LogLevel getLogLevel() {
        return this.logLevel;
    }
}
