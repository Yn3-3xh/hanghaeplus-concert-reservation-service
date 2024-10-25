package hanghaeplus.domain.point.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.BAD_REQUEST;
import static hanghaeplus.domain.common.error.ErrorStatus.CONFLICT;

@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode {

    INVALID_USER_ID(BAD_REQUEST, "유효하지 않은 유저ID 입니다.", LogLevel.WARN),
    INVALID_AMOUNT(BAD_REQUEST, "유효하지 않은 금액입니다.", LogLevel.WARN),
    INVALID_STATUS(BAD_REQUEST, "유효하지 않은 상태값입니다.", LogLevel.WARN),
    INSUFFICIENT_POINTS(CONFLICT, "포인트가 부족합니다.", LogLevel.WARN),
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
