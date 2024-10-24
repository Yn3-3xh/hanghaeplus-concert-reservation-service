package hanghaeplus.application.queue.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {

    NOT_FOUND_CONCERT_QUEUE(NOT_FOUND, "콘서트의 대기열이 존재하지 않습니다.", LogLevel.INFO),
    NOT_FOUND_QUEUE_TOKEN(NOT_FOUND, "대기열 토큰이 존재하지 않습니다.", LogLevel.INFO),
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
